package com.qiaomu.modules.workflow.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.modules.workflow.entity.UserWorkflow;
import com.qiaomu.modules.workflow.entity.YwWorkflowMessage;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-05 23:22
 */
public interface UserWorkflowService extends IService<UserWorkflow> {
    void changeInfo(YwWorkflowMessage process);

    List<UserWorkflow> getUserWorkflows(Long userId, Long communityId);
}
