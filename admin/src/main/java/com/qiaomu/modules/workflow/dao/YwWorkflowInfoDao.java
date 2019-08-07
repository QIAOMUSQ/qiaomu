package com.qiaomu.modules.workflow.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.qiaomu.modules.workflow.entity.YwWorkflowInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 15:59
 */
public interface YwWorkflowInfoDao extends BaseMapper<YwWorkflowInfo> {

    List<YwWorkflowInfo> getAll(YwWorkflowInfo workflowInfo);


    List<YwWorkflowInfo> selectPageAll(Pagination page , YwWorkflowInfo info);

    List<YwWorkflowInfo> getAllWorker(YwWorkflowInfo workflowInfo);
}
