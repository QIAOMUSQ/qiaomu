package com.qiaomu.modules.auth.service;

import com.qiaomu.modules.sys.entity.SysUserEntity;

/**
 * @author 李品先
 * @description: 用户登录后存储用户信息
 * @Date 2019-11-06 21:57
 */
public interface AuthLoginService {

    /**
     * 保存用户信息至redis中
     * @param sysUserEntity 用户信息
     * @param sessionId sessionId
     */
    void saveUserInfoToRedis(SysUserEntity sysUserEntity,String sessionId);

    SysUserEntity getUserInfoToRedis(String sessionId);
}
