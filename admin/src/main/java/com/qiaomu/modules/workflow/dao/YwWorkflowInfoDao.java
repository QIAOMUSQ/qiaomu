package com.qiaomu.modules.workflow.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.workflow.entity.YwWorkflowInfo;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 15:59
 */
public interface YwWorkflowInfoDao extends BaseMapper<YwWorkflowInfo> {

    List<YwWorkflowInfo> getAll(YwWorkflowInfo workflowInfo);


}
