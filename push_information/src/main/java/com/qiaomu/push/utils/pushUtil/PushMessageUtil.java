package com.qiaomu.push.utils.pushUtil;

import com.gexin.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.qiaomu.push.entity.PushMessage;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 李品先
 * @description:推送工具类
 * @Date 2019-11-07 15:26
 */
public class PushMessageUtil {

    /**
     * 推送给所有人
     * @param pushMessage
     */
    public static void pushAll(PushMessage pushMessage){
        IGtPush push = new IGtPush(PushParameter.URL, PushParameter.APP_KEY, PushParameter.MASTER_SECRET);
        List<String> appIds = new ArrayList<String>();
        appIds.add(PushParameter.APP_ID);
        AppMessage message = new AppMessage();
        message.setData(Template.notificationTemplateDemo(pushMessage));
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);  // 时间单位为毫秒
        // STEP6：执行推送
        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
    }

    /**
     * 推送给单人批量信息
     * @param messageList 信息list
     * @param clientId 推送对象clientID
     */
    public static void pushToSinglePeopleBatchInfo(List<PushMessage> messageList,String clientId){
        IGtPush push = new IGtPush(PushParameter.URL, PushParameter.APP_KEY, PushParameter.MASTER_SECRET);
        IBatch batch = push.getBatch();
        try {
            for (PushMessage info:messageList){
                SingleMessage message = new SingleMessage();
                message.setData(Template.notificationTemplateDemo(info));
                message.setOffline(true);
                message.setOfflineExpireTime(360 * 1000);
                Target target = new Target();
                target.setAppId(PushParameter.APP_ID);
                target.setClientId(clientId);
                batch.add(message, target);
            }
            IPushResult result = batch.submit();
            System.out.println("result = [" + JSONObject.toJSONString(result) + "], clientId = [" + clientId + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 批量推送信息给单人
     * @param messageList 信息list
     */
    public static void batchPushToSinglePeople(List<PushMessage> messageList){
        IGtPush push = new IGtPush(PushParameter.URL, PushParameter.APP_KEY, PushParameter.MASTER_SECRET);
        IBatch batch = push.getBatch();
        try {
            for (PushMessage info:messageList){
                SingleMessage message = new SingleMessage();
                message.setData(Template.notificationTemplateDemo(info));
                message.setOffline(true);
                message.setOfflineExpireTime(360 * 1000);
                Target target = new Target();
                target.setAppId(PushParameter.APP_ID);
                target.setClientId(info.getClientId());
                batch.add(message, target);
            }
            IPushResult result = batch.submit();
            if (result.getResultCode().equals("ok")){
                System.out.println("messageList = [推送成功]");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void pushToSinglePeople(PushMessage pushMessage){
        IGtPush push = new IGtPush(PushParameter.URL, PushParameter.APP_KEY, PushParameter.MASTER_SECRET);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        message.setOfflineExpireTime(24 * 3600 * 1000);//过多久该消息离线失效（单位毫秒） 支持1-72小时*3600000毫秒
        message.setData(Template.notificationTemplateDemo(pushMessage));
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(PushParameter.APP_ID);
        target.setClientId(pushMessage.getClientId());
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
        if (ret.getResultCode().equals("ok")){
            System.out.println("messageList = [推送成功]");
        }
    }



}
