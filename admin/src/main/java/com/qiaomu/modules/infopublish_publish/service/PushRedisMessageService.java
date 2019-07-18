package com.qiaomu.modules.infopublish_publish.service;

import com.qiaomu.modules.infopublish_publish.entity.PushMessage;

/**
 * @author 李品先
 * @description: 消息推送
 * @Date 2019-07-14 22:35
 */
public interface PushRedisMessageService {

    /**
     * 推送信息服务
     * type:类型
     *  0:推送到个人
     *  1：推送到群组
     *  2：推送到社区
     *  3：推送到全部用户
     * @param messages
     */
    void pushMessageToRedis(PushMessage messages);
}
