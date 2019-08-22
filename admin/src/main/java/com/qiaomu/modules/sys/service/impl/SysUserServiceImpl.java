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

package com.qiaomu.modules.sys.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.annotation.DataFilter;
import com.qiaomu.common.utils.AESUtil;
import com.qiaomu.common.utils.Constant;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.datasources.DataSourceNames;
import com.qiaomu.datasources.annotation.DataSource;
import com.qiaomu.modules.sys.dao.SysUserDao;
import com.qiaomu.modules.sys.entity.SysDeptEntity;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.UserExtend;
import com.qiaomu.modules.sys.service.SysDeptService;
import com.qiaomu.modules.sys.service.SysUserRoleService;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.sys.service.UserExtendService;
import com.qiaomu.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service
@DataSource(name = DataSourceNames.FIRST)
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private UserExtendService userExtendService;

    @Autowired
    private SysUserDao sysUserDao;


    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");

        Page<SysUserEntity> page = this.selectPage(
                new Query<SysUserEntity>(params).getPage(),
                new EntityWrapper<SysUserEntity>()
                        .like(StringUtils.isNotBlank(username), "username", username)
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );

        for (SysUserEntity sysUserEntity : page.getRecords()) {
            SysDeptEntity sysDeptEntity = sysDeptService.selectById(sysUserEntity.getDeptId());
            if (sysDeptEntity != null) {
                sysUserEntity.setDeptName(sysDeptEntity.getName());
            } else {
                sysUserEntity.setDeptName("");
            }
        }

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysUserEntity user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
        this.insert(user);
        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserEntity user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
        }
        this.updateById(user);
        UserExtend userExtend  =  userExtendService.getUserCommunity(user.getUserId());
        if(userExtend ==null){
            userExtend = new UserExtend();
        }
        if (user.getPropertyCompanyRoleType() !=null) {
            //5：游客  4：业主  3:物业工作人员 2:物业管理员
            userExtend.setCompanyRoleType(user.getPropertyCompanyRoleType());
        }
        userExtend.setCheck("1");
        if(userExtend.getId()==null){
            userExtend.setUserId(user.getUserId());
            userExtendService.insert(userExtend);
        }else {
            userExtendService.updateById(userExtend);
        }


        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }



    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new EntityWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
    }

    @Override
    public SysUserEntity queryById(Long userId) {
        return baseMapper.queryById(userId);
    }

    @Override
    public List<SysUserEntity> getLoginUser(Long deptId) {
        return baseMapper.getLoginUser(deptId);
    }

    @Override
    public SysUserEntity isExist(String phone) {

        return baseMapper.getUserByUserName(phone);

    }

    @Override
    public String getUserNameByIds(String idString,String type) {
        String names = "";
        if(StringUtils.isNotBlank(idString)){
            SysUserEntity user = baseMapper.getUserNameInIds(idString);
            names =  user.getUsername();
        }
        return names;
    }

    @Override
    public String getUserIdsByPhones(String phones, String type) {
        String ids = "";
        if(StringUtils.isNotBlank(phones)){
            String[] phoneS = phones.split(type);
            for(String phone : phoneS){
                if(StringUtils.isNotBlank(phone)){
                    ids += baseMapper.getUserByUserName(phone).getUserId()+",";
                }
            }
            if (ids.length()>0){
                ids = ids.substring(0,ids.length()-1);
            }
        }
        return ids;
    }

    @Override
    public String queryUserImageUrl(String userId) {
        return sysUserDao.queryUserImageUrl(userId);
    }

    @Override
    public String getRealNameByIds(String ids) {
        String name = "";
        if(StringUtils.isNotBlank(ids)){
            System.out.println("ids = [" + ids + "]");
            SysUserEntity user = baseMapper.getuserRealName(ids);
            String realName=user.getRealName();
            if(StringUtils.isNotBlank(realName)){
                String[] names = realName.split(",");
                for (String str : names){
                    name+= AESUtil.decrypt(str)+",";
                }
                if (name.length()>0){
                    name = name.substring(0,name.length()-1);
                }
            }
        }
        return name;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<Long> userIds) {
        userExtendService.deleteByUserIds(userIds);
        deleteBatchIds(userIds);
        sysUserRoleService.deleteByUserIds(userIds);
    }
}
