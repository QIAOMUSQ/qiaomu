package com.qiaomu.modules.auditprocess.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.exception.RRException;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.article.exception.CommentException;
import com.qiaomu.modules.auditprocess.entity.YwWorkflowMessage;
import com.qiaomu.modules.auditprocess.service.YwWorkflowInfoService;
import com.qiaomu.modules.auditprocess.service.YwWorkflowMessageService;
import com.qiaomu.modules.sys.controller.AbstractController;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.UserExtend;
import org.apache.commons.lang.StringUtils;
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
    public R list(@RequestParam Map<Object, Object> params) {
        params.put("companyId", getCompanyId());
        PageUtils page = this.WorkflowCheckService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 保存流程信息
     * @param userId 用户id
     * @param location  处理位置
     * @param detail    事件详情
     * @param pictureId 图片id字符串
     * @param serviceDate 维修时间
     * @param workflowId 流程ID
     * @param communityId  社区ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveWorkflowInfo",method = RequestMethod.POST)
    public Object saveWorkflowInfo(Long userId,String location,
                              String detail,String pictureId,
                              String serviceDate,Long workflowId,
                              Long communityId){
        try {
            if(communityId != null){
               String info = WorkflowCheckService.saveWorkflowInfo(userId,
                        location,detail,
                        pictureId,serviceDate,
                        workflowId,communityId);
                return JSON.toJSONString(BuildResponse.success(info));
            }else {
                return JSON.toJSONString(BuildResponse.fail("请进行个人信息验证"));
            }
        }catch (Exception e){
            if(e instanceof RRException){
                return JSON.toJSONString(BuildResponse.fail("1001",((RRException) e).getMsg()));
            }else{
                return JSON.toJSONString(BuildResponse.fail());
            }
        }
    }


    /**
     * 获取用户申请流程信息
     * @param userId 用户id
     * @param workflowType  工作流类型 1:供水维修  2:电力报修  3:煤气维修   4:建筑报修
     * @param type  流程状态 0：申请 1：一级接收 11：一级受理完成 2：二级接收 21：二级受理完成  3：上报  4：通过  5：不通过 6:终止
     * @param communityId 社区ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getUserApplyWorkflowInfo",method = RequestMethod.POST)
    public Object getUserApplyWorkflowInfo(Long userId,Long communityId,String workflowType, String type,Integer page,Integer limit){
        /*Map<Object, Object> params = new HashMap<>();
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        params.put("workflowType",workflowType);
        params.put("type",type);
        if(communityId == null) {
            SysUserEntity user = getUser();
            if(user.getCompanyId() != null && !"".equals(user.getCompanyId()) ){
                params.put("companyId",user.getCompanyId());
            }else {
                return BuildResponse.fail("请进行个人信息验证");
            }
        }else {
            params.put("communityId",communityId);
        }*/

        return BuildResponse.success(JSON.toJSON(this.WorkflowCheckService.getAll(userId,communityId,workflowType,type)));
    }

    /**
     * 更新用户流程信息
     * @param opinionSuperintendent 监管人意见
     * @param opinionOne 第一处理人意见
     * @param opinionTwo 第二处理人意见
     * @param opinionReport 上报人意见
     * @param userOpinion 用户意见
     * @param type 流程状态 0：申请 1：一级受理 11：一级受理完成 2：二级受理 21：二级受理完成  3：上报  4：通过  5：不通过 6:终止
     * @param id 流程信息ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateUserWorkflowInfo",method = RequestMethod.POST)
    public Object updateUserWorkflowInfo(String opinionSuperintendent,String opinionOne,
                                    String opinionTwo,String opinionReport,
                                    String userOpinion,String type,Long id){
        Boolean info = WorkflowCheckService.updateUserWorkflowInfo(opinionSuperintendent,opinionOne,opinionTwo, opinionReport,userOpinion,type,id);
        if(info){
            return BuildResponse.success("更新成功");
        }else {
            return BuildResponse.fail("更新失败");
        }

    }


    /**
     * 根据ID获取流程信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getUserWorkflowById",method = RequestMethod.POST)
    public Object getUserWorkflowById(Long id){
        return BuildResponse.success(JSON.toJSON(WorkflowCheckService.selectById(id)));
    }



    /**
     * 获取社区或者物业流程信息
     * @param workflowType 字典值 1:供水维修  2:电力报修  3:煤气维修   4:建筑报修
     * @param page 当前页
     * @param limit 每页数
     * @param communityId 社区id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getCompanyCommunityWorkFlowInfo",method = RequestMethod.POST)
    public Object getCompanyCommunityWorkFlowInfo(Long communityId,String workflowType,Integer page,Integer limit){
        Map<String, Object> params = new HashMap<>();
        params.put("page",page);
        params.put("limit",limit);
        params.put("workflowType",workflowType);

        if(communityId == null) {
            SysUserEntity user = getUser();
            if(user.getCompanyId() != null && !"".equals(user.getCompanyId()) ){
                params.put("companyId",user.getCompanyId());
            }else {
                return JSON.toJSONString(BuildResponse.fail("请进行个人信息验证"));
            }
        }else {
            params.put("communityId",communityId);
        }
        PageUtils pageUtil = this.workflowMessageService.queryPage(params);
        return BuildResponse.success(JSON.toJSON(pageUtil));

    }
}