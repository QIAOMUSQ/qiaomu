package io.renren.modules.tencentCloud.sms;

/**
 * @author 李品先
 * @description:腾讯云短信推送配置
 * @Date 2018-10-21 22:13
 */
public interface SmsInfoConfig {

    /****
     * SDK AppID是短信应用的唯一标识，调用短信API接口时需要提供该参数。
     */
    final Integer AppID = 1400154297;

    /**
     * App Key是用来校验短信发送请求合法性的密码，与SDK AppID对应，需要业务方高度保密，切勿把密码存储在客户端。
     */
    final String AppKey = "05601287f8519b62fea3d4cbe2d332e8";


}
