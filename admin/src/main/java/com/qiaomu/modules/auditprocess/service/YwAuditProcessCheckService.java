package com.qiaomu.modules.auditprocess.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.auditprocess.entity.YwAuditProcessCheck;

import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 16:02
 */
public interface YwAuditProcessCheckService extends IService<YwAuditProcessCheck> {
    PageUtils queryPage(Map<String, Object> paramMap);
}