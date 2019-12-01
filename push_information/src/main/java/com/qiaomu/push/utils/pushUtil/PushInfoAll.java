package com.qiaomu.push.utils.pushUtil;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-11-06 15:51
 */
public class PushInfoAll {
    // STEP1：获取应用基本信息
    private static String appId = "w5unXLw5Hm8tz5P4Swozi7";
    private static String appKey = "kQAUx5urHq75cNi9nZuL17";
    private static String masterSecret = "vNgAvCy7YP7ardJzlSLPd8";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void main(String[] args) throws IOException {

        IGtPush push = new IGtPush(url, appKey, masterSecret);

        Style0 style = new Style0();
        // STEP2：设置推送标题、推送内容
        style.setTitle("测试图标");
        style.setText("测试网络图标");
        //style.setLogo("push.png");  // 设置推送图标
        // 配置通知栏网络图标
        style.setLogoUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1573038216709&di=950c92c149c9b4cc3ca744f4705ef5b3&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F01%2F54%2F03%2F90%2F5930149711114.png");
        // STEP3：设置响铃、震动等推送效果
        style.setRing(true);  // 设置响铃
        style.setVibrate(true);  // 设置震动


        // STEP4：选择通知模板
        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setStyle(style);
        template.setTransmissionType(2);
        template.setTransmissionContent("这是透传信息1");


        // STEP5：定义"AppMessage"类型消息对象,设置推送消息有效期等推送参数
        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);  // 时间单位为毫秒

        // STEP6：执行推送
        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
    }

}
