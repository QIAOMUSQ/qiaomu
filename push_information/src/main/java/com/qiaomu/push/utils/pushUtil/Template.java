package com.qiaomu.push.utils.pushUtil;

import com.gexin.fastjson.JSONObject;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.VoIPPayload;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.StartActivityTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.qiaomu.push.entity.PushMessage;

/**
 * @author 李品先
 * @description:模板
 * @Date 2019-11-08 16:18
 */
public class Template {
    /**
     * 自定义模板
     * @return
     */
    public static TransmissionTemplate transmissionTemplateDemo(PushMessage message) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(PushParameter.APP_ID);
        template.setAppkey(PushParameter.APP_KEY);
        template.setTransmissionType(2);
        template.setAPNInfo(getAPNPayload(message)); //ios消息推送
        template.setTransmissionContent("请输入需要透传的内容");
        return template;
    }


    /**
     * 打开应用首页模板
     * @param message
     * @return
     */
    public static NotificationTemplate notificationTemplateDemo(PushMessage message) {
        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(PushParameter.APP_ID);
        template.setAppkey(PushParameter.APP_KEY);
        template.setTransmissionType(1);
        template.setTransmissionContent("请输入您要透传的内容");
        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(message.getInfoType());
        style.setText(message.getMessage());
        // 配置通知栏网络图标
        style.setLogoUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1573038216709&di=950c92c149c9b4cc3ca744f4705ef5b3&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F01%2F54%2F03%2F90%2F5930149711114.png");
        style.setChannelLevel(3);
        template.setStyle(style);
        template.setTransmissionContent(message.getTransmissionContent());
        // 消息覆盖
        // template.setNotifyid(123); // 在消息推送的时候设置自定义的notifyid。如果需要覆盖此条消息，则下次使用相同的notifyid发一条新的消息。客户端sdk会根据notifyid进行覆盖。
        return template;
    }

    /**
     * 打开应用内页面模板
     * @param message 消息
     * @return
     */
    public static StartActivityTemplate startActivityTemplateDemo(PushMessage message) {
        StartActivityTemplate template = new StartActivityTemplate();
        template.setAppId(PushParameter.APP_ID);
        template.setAppkey(PushParameter.APP_KEY);
        Style0 style = new Style0();
        style.setTitle(message.getInfoType());
        style.setText(message.getMessage());

        style.setLogoUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1573038216709&di=950c92c149c9b4cc3ca744f4705ef5b3&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F01%2F54%2F03%2F90%2F5930149711114.png");
        template.setStyle(style);
        // template.setAPNInfo(getAPNPayload(message)); //ios消息推送
        String intent = "intent:#Intent;component=com.yourpackage/.NewsActivity;S.parm1=value1;S.parm2=value2;end";
        template.setIntent(intent); //最大长度限制为1000
        //  template.setNotifyid(123); // 在消息推送的时候设置自定义的notifyid。如果需要覆盖此条消息，则下次使用相同的notifyid发一条新的消息。客户端sdk会根据notifyid进行覆盖。
        return template;
    }

    private static APNPayload getAPNPayload(PushMessage message) {
        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("+1");
        payload.setContentAvailable(1);
        //ios 12.0 以上可以使用 Dictionary 类型的 sound
        payload.setSound("default");
        payload.setCategory("$由客户端定义");
        payload.addCustomMsg("由客户自定义消息key", "由客户自定义消息value");

        //简单模式APNPayload.SimpleMsg
        payload.setAlertMsg(getDictionaryAlertMsg(message));
        //payload.setAlertMsg(getDictionaryAlertMsg());  //字典模式使用APNPayload.DictionaryAlertMsg

        //设置语音播报类型，int类型，0.不可用 1.播放body 2.播放自定义文本
        payload.setVoicePlayType(2);
        //设置语音播报内容，String类型，非必须参数，用户自定义播放内容，仅在voicePlayMessage=2时生效
        //注：当"定义类型"=2, "定义内容"为空时则忽略不播放
        payload.setVoicePlayMessage(message.getMessage());
        return payload;
    }



    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(PushMessage message) {
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody(message.getMessage());
        alertMsg.setTitle(message.getInfoType());
       /* alertMsg.setActionLocKey("显示关闭和查看两个按钮的消息");
        alertMsg.setLocKey("loc-key1");
        alertMsg.addLocArg("loc-ary1");
        alertMsg.setLaunchImage("调用已经在应用程序中绑定的图形文件名");
        // iOS8.2以上版本支持
        alertMsg.setTitleLocKey("自定义通知标题");
        alertMsg.addTitleLocArg("自定义通知标题组");*/
        return alertMsg;
    }

    /**
     * 需要使用iOS语音传输，请使用VoIPPayload代替APNPayload
     * @return
     */
    private static VoIPPayload getVoIPPayload() {
        VoIPPayload payload = new VoIPPayload();
        JSONObject jo = new JSONObject();
        jo.put("key1", "value1");
        payload.setVoIPPayload(jo.toString());
        return payload;
    }
}
