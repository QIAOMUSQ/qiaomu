package com.qiaomu.push.service;

import com.qiaomu.push.entity.User;

/**
 * @author 李品先
 * @description:
 * @Date 2019-11-11 11:18
 */
public interface RedisService {

    User getRedisUserInfo(String userNameKey);
}
