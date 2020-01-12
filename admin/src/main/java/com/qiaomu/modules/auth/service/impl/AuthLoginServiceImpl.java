package com.qiaomu.modules.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.RedisKeys;
import com.qiaomu.modules.auth.service.AuthLoginService;
import com.qiaomu.modules.auth.service.KafkaTemplateService;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonSimpleJsonParser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 李品先
 * @description:用户登录认证实现类
 * @Date 2019-11-06 21:59
 */
@Service
public class AuthLoginServiceImpl implements AuthLoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private KafkaTemplateService kafkaTemplateService;

    @Override
    public void saveUserInfoToRedis(SysUserEntity sysUserEntity, String sessionId) {
        setRedisUserInfo(RedisKeys.getUserInfoByUserNameKey(sysUserEntity.getUsername()),sysUserEntity);
        setRedisUserInfo(RedisKeys.getUserInfoBySessionIdKey(sessionId),sysUserEntity);
        kafkaTemplateService.loginTopic(sysUserEntity);
    }

    @Override
    public SysUserEntity getUserInfoToRedis(String sessionId) {
        return getRedisUserInfo(sessionId);
    }

    private SysUserEntity getRedisUserInfo(String key) {
        String userJson = (String)redisTemplate.opsForValue().get(key);
        if (null !=userJson && !"".equals(userJson)){
            return JSON.parseObject(userJson,SysUserEntity.class);
        }
        return new SysUserEntity();
    }

    private void setRedisUserInfo(String key, SysUserEntity userEntity){
        redisTemplate.opsForValue().set(key, JSON.toJSONString(userEntity));
        //60分钟过期
        redisTemplate.expire(key, 60, TimeUnit.MINUTES);
    }
}
