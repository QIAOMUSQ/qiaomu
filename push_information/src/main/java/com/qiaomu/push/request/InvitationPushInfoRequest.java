package com.qiaomu.push.request;

import com.alibaba.fastjson.JSON;
import com.qiaomu.push.entity.PushMessage;
import com.qiaomu.push.entity.User;
import com.qiaomu.push.service.PushMessageService;
import com.qiaomu.push.service.RedisService;
import com.qiaomu.push.service.UserService;
import com.qiaomu.push.utils.RedisKeys;
import com.qiaomu.push.utils.pushUtil.PushMessageUtil;
import org.joda.time.DateTime;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李品先
 * @description:公告推送信息
 * @Date 2019-11-08 16:55
 */
public class InvitationPushInfoRequest implements Request{

    private PushMessage message;

    private PushMessageService pushMessageService;

    private RedisService redisService;

    private UserService userService;

    @Override
    public void process() {
        if (message.getCommunityId() !=null){
            List<User> userList = userService.findUserByCommunityId(message.getCommunityId());
            List<PushMessage> pushMessageList = new ArrayList<>();//需要推送的信息
            List<PushMessage> saveMessageList = new ArrayList<>();//该用户还未登录，需要推送的信息
            for (User user:userList){
                //选中角色为业主的人员
                if(user.getCommunityRoleType().equals("2")){
                    User loginUser = redisService.getRedisUserInfo(RedisKeys.getUserInfoByUserNameKey(user.getUsername()));
                    PushMessage singlePeople = message.clone();
                    //判断用户是否离线
                    if(loginUser != null){
                        singlePeople.setReceivePhone(user.getUsername());
                        singlePeople.setClientId(user.getClientId());
                        singlePeople.setTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                        singlePeople.setStatus(true);
                        pushMessageList.add(singlePeople);
                    }else {
                        singlePeople.setReceivePhone(user.getUsername());
                        singlePeople.setClientId(user.getClientId());
                        singlePeople.setStatus(false);
                    }
                    saveMessageList.add(singlePeople);
                }

            }
            //将在线用户信息实时推送
            if (pushMessageList.size()>0){
                PushMessageUtil.batchPushToSinglePeople(pushMessageList);
            }
            //将还离线用户推送信息入库，等待其下次登录时候推送
            if (saveMessageList.size()>0){
                pushMessageService.insertBatch(saveMessageList,saveMessageList.size());
            }

        }

    }

    public InvitationPushInfoRequest(PushMessage message, PushMessageService pushMessageService, RedisService redisService, UserService userService) {
        this.message = message;
        this.pushMessageService = pushMessageService;
        this.redisService = redisService;
        this.userService = userService;
    }


}
