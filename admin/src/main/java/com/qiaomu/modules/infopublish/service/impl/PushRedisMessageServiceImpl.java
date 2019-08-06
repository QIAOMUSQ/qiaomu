package com.qiaomu.modules.infopublish.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiaomu.modules.infopublish.service.PushRedisMessageService;
import com.qiaomu.modules.infopublish.entity.PushMessage;
import com.qiaomu.modules.sys.service.SysUserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 李品先
 * @description:
 * @Date 2019-07-14 22:37
 */
@Service
public class PushRedisMessageServiceImpl implements PushRedisMessageService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    private SysUserService userService;
    /**
     * type:类型
     *  0:推送到个人
     *  1：推送到群组
     *  2：推送到社区
     *  3：推送到全部用户
     *  type=0时，接收人phone一定不为空
     * @param message
     */
    @Override
    public void pushMessageToRedis(PushMessage message) {
       // byte[] msg =jackson2JsonRedisSerializer.serialize(message);
        stringRedisTemplate.convertAndSend("message",JSON.toJSONString(message));
        redisTemplate.boundHashOps("message_history").put("history_"+message.getPhone()+"_"+message.getTime().replace(" ","").replace("-","").replace(":",""),JSON.toJSONString(message));
    }

    /**
     *
     * @param userId 推送人Id
     * @param idString 被推送人Id
     * @param infoType 推送信息类型（比如：爱心银行推送，车位推送，报修申请推送）
     * @param type 推送类型（0：推送到个人，2：推送到社区）
     * @param message 信息
     */
    @Override
    public void pushMessage(Long userId, String idString, String infoType,String type, String message) {
        String[] ids = idString.split(",");
        for (String id : ids){
            String phone  = userService.queryById(Long.valueOf(id)).getUsername();
            String userPhone = userService.queryById(userId).getUsername();
            PushMessage data = new PushMessage(phone,type,infoType, DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),message,userPhone);
            stringRedisTemplate.convertAndSend("message",JSON.toJSONString(data));
            redisTemplate.boundHashOps("message_history").put("history_"+data.getPhone()+"_"+data.getTime().replace(" ","").replace("-","").replace(":",""),JSON.toJSONString(data));
        }
    }
}
