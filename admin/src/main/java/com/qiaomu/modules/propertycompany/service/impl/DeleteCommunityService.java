package com.qiaomu.modules.propertycompany.service.impl;

import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import com.qiaomu.modules.sys.entity.YwCommunity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-29 0:00
 */
@Service
public class DeleteCommunityService {

    @Autowired
    private YwCommunityService communityService;

    /**
     *  和社区关联信息表
     * yw_advertise
     * yw_carport  车辆表
     * yw_community_advertise 社区商户关联表
     * yw_community_login_statistics 社区统计表
     * yw_invitation 公告表
     * yw_user_extend 用户扩展信息表
     * yw_user_workflow 工作人员社区流程关联表
     * yw_workflow_info 社区流程表
     * yw_workflow_message  社区流程管理
     * pluto_article  发帖表
     * pluto_article_comment
     * push_message_data 推送信息表
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void deleteInfo(YwCommunity community) throws InterruptedException {
        //删除社区表信息
        communityService.deleteById(community.getId());

    }
}
