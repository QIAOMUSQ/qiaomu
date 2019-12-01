package com.qiaomu.push.request;

import com.qiaomu.push.entity.PushMessage;
import com.qiaomu.push.entity.User;
import com.qiaomu.push.service.PushMessageService;
import com.qiaomu.push.service.RedisService;
import com.qiaomu.push.service.UserService;
import com.qiaomu.push.utils.RedisKeys;
import com.qiaomu.push.utils.pushUtil.PushMessageUtil;
import org.joda.time.DateTime;

/**
 * @author 李品先
 * @description: 维修信息推送请求
 * @Date 2019-11-11 11:09
 */
public class RepairsInfoRequest implements Request {

    private PushMessage message;

    private PushMessageService pushMessageService;

    private RedisService redisService;

    @Override
    public void process() {
        User loginUser = redisService.getRedisUserInfo(RedisKeys.getUserInfoByUserNameKey(message.getReceivePhone()));
        if (loginUser!=null){
            message.setClientId(loginUser.getClientId());
            message.setTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            message.setStatus(true);
        }else {
            message.setStatus(false);
        }
        PushMessageUtil.pushToSinglePeople(message);
        pushMessageService.insert(message);

    }

    public RepairsInfoRequest(PushMessage message, PushMessageService pushMessageService, RedisService redisService) {
        this.message = message;
        this.pushMessageService = pushMessageService;
        this.redisService = redisService;
    }
}
