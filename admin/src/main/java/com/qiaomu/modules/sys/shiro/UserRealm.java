/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.qiaomu.modules.sys.shiro;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.Constant;
import com.qiaomu.modules.sys.dao.SysMenuDao;
import com.qiaomu.modules.sys.dao.SysUserDao;
import com.qiaomu.modules.sys.entity.SysMenuEntity;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * 认证
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 上午11:55:49
 */
@Component
public class UserRealm extends AuthorizingRealm {

    private static final String SHIRO_LOGIN_COUNT = "shiro_login_count_";
    private static final String SHIRO_IS_LOCK = "shiro_is_lock_";

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
        Long userId = user.getUserId();

        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenuEntity> menuList = sysMenuDao.selectAllList();
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        String name = token.getUsername();
        String password = String.valueOf(token.getPassword());
        //查询用户信息
        SysUserEntity user = new SysUserEntity();
        user = sysUserDao.getUserByUserName(name);
        //账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        //账号锁定
        if (user.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        if (ShiroUtils.sha256(password, user.getSalt()).equals(user.getPassword())) {
            //如果正确,从缓存中将用户登录计数 清除
            if(opsForValue.get(SHIRO_LOGIN_COUNT+name)!=null){
                opsForValue.set(SHIRO_LOGIN_COUNT+name,"0");
                opsForValue.set(SHIRO_IS_LOCK+name, "0");
                stringRedisTemplate.delete(SHIRO_IS_LOCK+name);
            }
        }else {
            opsForValue.increment(SHIRO_LOGIN_COUNT+name, 1);
            //计数大于5时，设置用户被锁定一小时
            if(Integer.parseInt(opsForValue.get(SHIRO_LOGIN_COUNT+name))>=5){
                opsForValue.set(SHIRO_IS_LOCK+name, "LOCK");
                stringRedisTemplate.expire(SHIRO_IS_LOCK+name, 30, TimeUnit.MINUTES);
            }
            if ("LOCK".equals(opsForValue.get(SHIRO_IS_LOCK+name))){
                throw new DisabledAccountException("密码输入错误大于5次，帐号锁定一小时");
            }
        }

        //记住用户
        token.setRememberMe(true);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
        System.out.println("user = [" + JSON.toJSONString(user) + "] getSalt=="+ByteSource.Util.bytes(user.getSalt()));
        return info;
    }

    /**
     * 凭证匹配器
     * @param credentialsMatcher
     */
     @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
         HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
        shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
        shaCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }


}
