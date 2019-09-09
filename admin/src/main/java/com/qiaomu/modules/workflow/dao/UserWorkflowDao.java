package com.qiaomu.modules.workflow.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.workflow.entity.UserWorkflow;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-05 23:07
 */
public interface UserWorkflowDao extends BaseMapper<UserWorkflow> {

    List<UserWorkflow> selectByworkflowId(Long workflowId);

    List<UserWorkflow> getUserWorkflows(UserWorkflow workflow);

    void deleteByCommunity(Long communityId);
}
