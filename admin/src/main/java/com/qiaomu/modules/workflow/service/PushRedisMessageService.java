package com.qiaomu.modules.workflow.service;

import com.qiaomu.modules.workflow.VO.PushMessageVO;

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
    void pushMessageToRedis(PushMessageVO messages);

    /**
     * 推送信息服务
     * @param pushUserId 推送Id
     * @param receiveIdS 被推送人Id
     * @param infoType 推送信息类型（比如：爱心银行推送，车位推送，报修申请推送）
     * @param type 推送类型（0：推送到个人，2：推送到社区）
     * @param message 信息
     * @param communityId 社区id
     */
    void pushMessage(Long pushUserId,String receiveIdS,String infoType,String type,String message,Long communityId);
}