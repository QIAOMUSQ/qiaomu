package com.qiaomu.modules.auditprocess.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.auditprocess.entity.YwWorkflowMessage;

import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 16:04
 */
public interface YwWorkflowMessageService extends IService<YwWorkflowMessage> {
    PageUtils queryPage(Map<String, Object> paramMap);

    void save(YwWorkflowMessage paramYwAuditProcessMessage);

    YwWorkflowMessage getById(Integer paramLong);
}
