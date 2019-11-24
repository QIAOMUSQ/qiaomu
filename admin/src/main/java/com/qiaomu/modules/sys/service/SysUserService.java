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

package com.qiaomu.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:43:39
 */
public interface SysUserService extends IService<SysUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 保存用户
     */
    void save(SysUserEntity user);

    /**
     * 修改用户
     */
    void update(SysUserEntity user);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     */
    boolean updatePassword(Long userId, String password, String newPassword);

    SysUserEntity queryById(Long userId);

    /**
     * 根据角色获取用户
     *
     * @param deptId
     * @return
     */
    List<SysUserEntity> getLoginUser(Long deptId);

    SysUserEntity isExist(String phone);

    /**
     * 根据id字符串和间隔字符类获取用户名
     * @param idString
     * @param type
     * @return
     */
    String getUserNameByIds(String idString,String type);

    String getUserIdsByPhones(String phones,String type);

    /**
     * 根据用户id查询用户头像信息
     * @param userId
     * @return
     */
    String queryUserImageUrl(String userId);

    String getRealNameByIds(String ids);

    /**
     * 通过IDlist集合删除用户
     * @param longs
     */
    void deleteByIds(List<Long> longs);

    /**
     * 设置用户最后一次登录的社区Id
     * @param userId
     * @param communityId
     */
    void setUserLoginCommunity(Long userId,Long communityId);

    /**
     * 通过手机号码和安全码查询用户信息
     * @param phone
     * @param securityCode
     * @return
     */
    SysUserEntity findBackPassword(String phone, String securityCode);

    /**
     * 更新用户登录的设备clientID
     * @param userEntity
     */
    void updateClientId(SysUserEntity userEntity);

    /**
     * 重设密码
     * @param user
     */
    void reSetPassword(SysUserEntity user);

}
