package com.qiaomu.modules.workflow.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.workflow.entity.YwWorkflowInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 16:02
 */
public interface YwWorkflowInfoService extends IService<YwWorkflowInfo> {
    PageUtils queryPage(Map<Object, Object> paramMap);

    /**
     * 保存流程信息
     * @param userId 用户手机
     * @param location 位置
     * @param detail    细节
     * @param pictureId 图片
     * @param serviceDate   上门维修时间
     * @param workflowId    流程ID
     * @param communityId 社区ID
     * @return
     */
    String saveWorkflowInfo(Long userId, String location, String detail, String pictureId, String serviceDate, Long workflowId, Long communityId,HttpServletRequest request);

    /**
     * 更新用户流程信息
     * @param opinionOne  第一处理人号码
     * @param userOpinion   用户意见
     * @param type  流程状态 0：申请 1：一级受理 11：一级受理完成 2：二级受理 21：二级受理完成  3：上报  4：通过  5：不通过 6:终止
     * @param id 流程信息ID
     * @return
     */
    Boolean updateUserWorkflowInfo(String opinionOne,  String userOpinion, String type, Long id,String starType);

    List<YwWorkflowInfo> getAll (Long userId,Long communityId,String workflowType, String type,String status);


    /**
     * 获取工作人员的需要处理流程
     * @param userId
     * @param communityId
     * @return
     */
    List<YwWorkflowInfo> getUserDetailWorkflow(Long userId, Long communityId);
}