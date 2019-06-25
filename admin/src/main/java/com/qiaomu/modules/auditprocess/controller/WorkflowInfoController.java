package com.qiaomu.modules.auditprocess.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.auditprocess.entity.YwWorkflowMessage;
import com.qiaomu.modules.auditprocess.service.YwWorkflowInfoService;
import com.qiaomu.modules.auditprocess.service.YwWorkflowMessageService;
import com.qiaomu.modules.sys.controller.AbstractController;
import com.qiaomu.modules.sys.entity.UserExtend;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description: 流程审核管理
 * @Date 2019-04-21 15:58
 */
@Controller
@RequestMapping("mobile/processCheck")
public class WorkflowInfoController extends AbstractController {

    @Autowired
    private YwWorkflowInfoService WorkflowCheckService;

    @Autowired
    private YwWorkflowMessageService workflowMessageService;

    @ResponseBody
    @RequestMapping(value = "process/list", method = RequestMethod.POST)
    @RequiresPermissions("process:check:list")
    public R list(@RequestParam Map<String, Object> params) {
        params.put("companyId", getCompanyOrCommunityByType("1"));
        PageUtils page = this.WorkflowCheckService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 保存流程信息
     * @param userPhone 用户号码
     * @param location  处理位置
     * @param detail    事件详情
     * @param pictureId 图片id字符串
     * @param ServiceDate 维修时间
     * @param WorkflowId 流程ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveWorkflowInfo",method = RequestMethod.POST)
    public R saveWorkflowInfo(String userPhone,String location,String detail,String pictureId,String ServiceDate,Long WorkflowId){
        UserExtend userExtend = getUserExtend(userPhone);
        if(userExtend.getCompanyId() != null || userExtend.getCommunityId() != null){
            String info = WorkflowCheckService.saveWorkflowInfo(userPhone,
                    location,detail,
                    pictureId,ServiceDate,
                    WorkflowId, userExtend.getCompanyId(),userExtend.getCommunityId());
            if(info.equals("success")){
                return R.ok(info,"保存成功");
            }else {
                return R.ok(info,"失败");
            }

        }else {
            return R.ok("error","请进行个人信息验证");
        }
    }



    /**
     * 根据字典值获取社区或者物业流程信息
     * @param userPhone 用戶賬戶
     * @param workflowType 字典值 1:供水维修  2:电力报修  3:煤气维修   4:建筑报修
     * @param page 当前页
     * @param limit 每页数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getCompanyCommunityWorkFlowInfoByDict",method = RequestMethod.POST)
    public R getCompanyCommunityWorkFlowInfoByDict(String userPhone,String workflowType,Integer page,Integer limit){
        UserExtend userExtend = getUserExtend(userPhone);

        if(userExtend.getCompanyId() != null || userExtend.getCommunityId() != null){
            Map<String, Object> params = new HashMap<>();
            params.put("page",page);
            params.put("limit",limit);
            params.put("phone",userPhone);
            params.put("workflowType",workflowType);
            if(userExtend.getCommunityId() != null){
                params.put("communityId",userExtend.getCommunityId());
            }
            if(userExtend.getCompanyId() != null){
                params.put("companyId",userExtend.getCompanyId());
            }
            PageUtils pageUtil = this.workflowMessageService.queryPage(params);
            return R.ok().put("page", pageUtil);
        }else {
            return R.ok("error","请进行个人信息验证");
        }
    }


    /**
     * 根据字典值和状态编码获取用户申请流程信息
     * @param userPhone 用户号码
     * @param workflowType  工作流类型 1:供水维修  2:电力报修  3:煤气维修   4:建筑报修
     * @param type  流程状态 0：申请 1：一级接收 11：一级受理完成 2：二级接收 21：二级受理完成  3：上报  4：通过  5：不通过 6:终止
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getUserApplyWorkflowInfo",method = RequestMethod.POST)
    public R getUserApplyWorkflowInfo(String userPhone,String workflowType,String type,Integer page,Integer limit){
        UserExtend userExtend = getUserExtend(userPhone);
        if(userExtend.getCompanyId() != null || userExtend.getCommunityId() != null){
            Map<String, Object> params = new HashMap<>();
            params.put("page",page);
            params.put("limit",limit);
            params.put("phone",userPhone);
            params.put("workflowType",workflowType);
            params.put("type",type);
            if(userExtend.getCommunityId() != null){
                params.put("communityId",userExtend.getCommunityId());
            }
            if(userExtend.getCompanyId() != null){
                params.put("companyId",userExtend.getCompanyId());
            }
            return R.ok().put("page", this.WorkflowCheckService.queryPage(params));
        }else {
            return R.ok("error","请进行个人信息验证");
        }
    }

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
    @ResponseBody
    @RequestMapping(value = "updateUserWorkflowInfo",method = RequestMethod.POST)
    public R updateUserWorkflowInfo(String userPhone,String detailOpinionOne,
                                    String detailOpinionTwo,String detailOpinionReport,
                                    String userOpinion,String type,Long id){
        Boolean info = WorkflowCheckService.updateUserWorkflowInfo(userPhone,detailOpinionOne,detailOpinionTwo, detailOpinionReport,userOpinion,type,id);
        if(info){
            return R.ok("success","更新成功");
        }else {
            return R.ok("error","更新失败");
        }

    }


    /**
     * 根据ID获取流程信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getUserWorkflowById",method = RequestMethod.POST)
    public R getUserWorkflowById(Long id){
        return R.ok("success", JSON.toJSON(WorkflowCheckService.selectById(id)));
    }

}