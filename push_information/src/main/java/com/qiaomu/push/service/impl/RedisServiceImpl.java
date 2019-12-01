package com.qiaomu.push.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiaomu.push.entity.User;
import com.qiaomu.push.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 李品先
 * @description:
 * @Date 2019-11-11 11:18
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public User getRedisUserInfo(String userNameKey) {
        String userJson = (String) redisTemplate.opsForValue().get(userNameKey);
        if (null != userJson && !"".equals(userJson)) {
            return JSON.parseObject(userJson, User.class);
        }
        return null;
    }

}
