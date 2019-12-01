package com.qiaomu.modules.auth.service;

import com.qiaomu.modules.workflow.VO.PushMessageVO;
import com.qiaomu.modules.sys.entity.SysUserEntity;

/**
 * @author 李品先
 * @description:
 * @Date 2019-11-08 16:44
 */
public interface KafkaTemplateService {

    /**
     * 用户登录
     * @param sysUserEntity
     */
    void loginTopic(SysUserEntity sysUserEntity);

    void pushInvitationInfo(PushMessageVO message);

    void pushRepairsInfo(PushMessageVO message);
}
