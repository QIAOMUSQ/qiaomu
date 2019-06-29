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
     * @param communityId 社区ID
     * @return
     */
    String saveWorkflowInfo(String userPhone, String location, String detail, String pictureId, String serviceDate, Long workflowId,Long communityId);

    /**
     * 更新用户流程信息
     * @param userPhone 用户手机
     * @param detailOpinionOne  第一处理人号码
     * @param detailOpinionTwo 第二处理人
     * @param detailOpinionReport  上报人
     * @param userOpinion   用户意见
     * @param type  流程状态 0：申请 1：一级受理 11：一级受理完成 2：二级受理 21：二级受理完成  3：上报  4：通过  5：不通过 6:终止
     * @param id 流程信息ID
     * @return
     */
    Boolean updateUserWorkflowInfo(String userPhone, String detailOpinionOne, String detailOpinionTwo, String detailOpinionReport, String userOpinion, String type, Long id);
}