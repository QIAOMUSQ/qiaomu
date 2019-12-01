package com.qiaomu.push.request;

import com.qiaomu.push.entity.PushMessage;
import com.qiaomu.push.entity.User;
import com.qiaomu.push.utils.pushUtil.PushMessageUtil;
import com.qiaomu.push.service.PushMessageService;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-11-07 11:07
 */
public class UserLoginRequest implements Request{


    private User user;

    private PushMessageService pushMessageService;

    public UserLoginRequest(User userEntity, PushMessageService pushMessageService) {
        this.user = userEntity;
        this.pushMessageService = pushMessageService;
    }

    @Override
    public void process() {
        //查询库中是否存在未发送信息
        List<PushMessage> pushMessages =  pushMessageService.findByReceivePhone(user.getUsername());
        if (pushMessages.size()>0){
            PushMessageUtil.pushToSinglePeopleBatchInfo(pushMessages,user.getClientId());
            for (PushMessage message:pushMessages){
                message.setStatus(true);
                message.setTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            }
            pushMessageService.updateBatchById(pushMessages,pushMessages.size());
        }
    }
}
