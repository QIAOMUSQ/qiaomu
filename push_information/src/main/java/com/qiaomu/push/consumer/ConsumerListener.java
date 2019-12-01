package com.qiaomu.push.consumer;

import com.alibaba.fastjson.JSON;
import com.qiaomu.push.entity.PushMessage;
import com.qiaomu.push.entity.User;
import com.qiaomu.push.request.InvitationPushInfoRequest;
import com.qiaomu.push.request.RepairsInfoRequest;
import com.qiaomu.push.request.Request;
import com.qiaomu.push.request.UserLoginRequest;
import com.qiaomu.push.service.PushMessageService;
import com.qiaomu.push.service.RedisService;
import com.qiaomu.push.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author 李品先
 * @description:用户登录消费
 * @Date 2019-11-08 10:04
 */
@Component
public class ConsumerListener {

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;
    /***
     * 用户登录检查推送信息
     * @param userString
     */
    @KafkaListener(topics = "userLoginTopic")
    public void userLoginRequest(String userString){
        User user = JSON.parseObject(userString,User.class);
        Request loginRequest = new UserLoginRequest(user,pushMessageService);
        loginRequest.process();
    }

    /**
     * 公告信息推送
     * @param invitationInfo
     */
    @KafkaListener(topics = "invitationTopic")
    public void invitationRequest(String invitationInfo){
        PushMessage message = JSON.parseObject(invitationInfo,PushMessage.class);
        Request loginRequest = new InvitationPushInfoRequest(message,pushMessageService,redisService,userService);
        loginRequest.process();
    }

    @KafkaListener(topics = "repairsTopic")
    public void repairsTopic(String info){
        PushMessage message = JSON.parseObject(info,PushMessage.class);
        Request repairsRequest = new RepairsInfoRequest(message,pushMessageService,redisService);
        repairsRequest.process();
    }
}
