package com.qiaomu.modules.infopublish_publish.service;

import com.qiaomu.modules.infopublish_publish.entity.PushMessage;

/**
 * @author 李品先
 * @description: 消息推送
 * @Date 2019-07-14 22:35
 */
public interface PushRedisMessageService {

    void pushMessageToRedis(PushMessage messages);
}
