package com.qiaomu.modules.auditprocess.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.auditprocess.entity.YwWorkflowInfo;

import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 16:02
 */
public interface YwWorkflowInfoService extends IService<YwWorkflowInfo> {
    PageUtils queryPage(Map<String, Object> paramMap);

    /**
     * 保存流程信息
     * @param userPhone 用户手机
     * @param location 位置
     * @param detail    细节
     * @param pictureId 图片
     * @param serviceDate   上门维修时间
     * @param workflowId    流程ID
     * @param companyId   公司ID
     * @param communityId 社区ID
     * @return
     */
    String saveWorkflowInfo(String userPhone, String location, String detail, String pictureId, String serviceDate, Integer workflowId,Integer companyId,Integer communityId);
}