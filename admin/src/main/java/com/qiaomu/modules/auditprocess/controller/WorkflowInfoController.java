package com.qiaomu.modules.auditprocess.controller;

import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.auditprocess.service.YwWorkflowInfoService;
import com.qiaomu.modules.sys.controller.AbstractController;
import com.qiaomu.modules.sys.entity.UserExtend;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping(value = "saveWorkflowInfo",method = RequestMethod.GET)
    public R saveWorkflowInfo(String userPhone,String location,String detail,String pictureId,String ServiceDate,Integer WorkflowId){
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
}