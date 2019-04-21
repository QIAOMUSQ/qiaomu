package com.qiaomu.modules.auditprocess.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.auditprocess.entity.YwAuditProcessMessage;

import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 16:04
 */
public interface YwAuditProcessMessageService extends IService<YwAuditProcessMessage>
{
    PageUtils queryPage(Map<String, Object> paramMap);

    void save(YwAuditProcessMessage paramYwAuditProcessMessage);

    YwAuditProcessMessage getById(Long paramLong);
}
