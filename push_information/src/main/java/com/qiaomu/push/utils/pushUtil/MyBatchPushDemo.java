package com.qiaomu.push.utils.pushUtil;

import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;

import java.io.IOException;

/**
 * @author 李品先
 * @description:
 * @Date 2019-11-07 16:41
 */
public class MyBatchPushDemo {
    // 详见【概述】-【服务端接入步骤】-【STEP1】说明，获得的应用配置
    private static String appId = "w5unXLw5Hm8tz5P4Swozi7";
    private static String appKey = "kQAUx5urHq75cNi9nZuL17";
    private static String masterSecret = "vNgAvCy7YP7ardJzlSLPd8";

    static String CID_A = "83928be2f2dc9f94af31b7ab6c5304a0";
    static String CID_B = "83928be2f2dc9f94af31b7ab6c5304a0";

    static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void main(String[] args) throws IOException {

        IIGtPush push = new IGtPush(host, appKey, masterSecret);
        IBatch batch = push.getBatch();

        try {
            // 构建客户a的透传消息a
           // constructClientTransMsg(CID_A,"msgA",batch);
            // 构建客户B的点击通知打开网页消息b
           // constructClientLinkMsg(CID_B,"msgB",batch);
        } catch (Exception e) {
            e.printStackTrace();
        }
        batch.submit();
    }

    private static void constructClientTransMsg(String cid, String msg ,IBatch batch) throws Exception {

        SingleMessage message = new SingleMessage();
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionContent(msg);
        template.setTransmissionType(2); // 透传消息接受方式设置，1：立即启动APP，2：客户端收到消息后需要自行处理

        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(360 * 1000);

        // 设置推送目标，填入appid和clientId
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(cid);
        batch.add(message, target);
    }

    private static void constructClientLinkMsg(String cid, String msg ,IBatch batch) throws Exception {

        SingleMessage message = new SingleMessage();
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle("请输入通知栏标题");
        style.setText("请输入通知栏内容");
        // 配置通知栏图标
        style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);

        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(360 * 1000);

        // 设置打开的网址地址
        template.setUrl("http://www.baidu.com");
        // 设置推送目标，填入appid和clientId
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(cid);
        batch.add(message, target);
    }
}
