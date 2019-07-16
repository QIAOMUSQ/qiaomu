package com.qiaomu.modules.infopublish_publish.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiaomu.modules.infopublish_publish.service.PushRedisMessageService;
import com.qiaomu.modules.infopublish_publish.entity.PushMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 李品先
 * @description:
 * @Date 2019-07-14 22:37
 */
@Service
public class PushRedisMessageServiceImpl implements PushRedisMessageService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void pushMessageToRedis(PushMessage message) {
        redisTemplate.convertAndSend("message",message);
       // redisTemplate.opsForList().rightPush("message_history", JSON.toJSONString(message));
        redisTemplate.boundHashOps("message_history").put("history_"+message.getPhone()+"_"+message.getTime(),JSON.toJSONString(message));
    }
}
