package com.qiaomu.modules.workflow.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.workflow.entity.YwWorkflowMessage;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 15:59
 */
public interface YwWorkflowMessageDao extends BaseMapper<YwWorkflowMessage> {
    List<YwWorkflowMessage> getAll(YwWorkflowMessage workflowMessage);

    @Override
    YwWorkflowMessage selectById(Serializable serializable);

    void deleteByCommunity(Long communityId);
}