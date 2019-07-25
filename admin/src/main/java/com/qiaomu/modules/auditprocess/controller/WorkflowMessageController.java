package com.qiaomu.modules.auditprocess.controller;

import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.auditprocess.entity.YwWorkflowMessage;
import com.qiaomu.modules.auditprocess.service.YwWorkflowMessageService;
import com.qiaomu.modules.sys.controller.AbstractController;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * @author 李品先
 * @description: 流程管理
 * @Date 2019-04-21 15:57
 */
@Controller
@RequestMapping("processMessage")
public class WorkflowMessageController extends AbstractController {

    @Autowired
    private YwWorkflowMessageService workflowMessageService;

    /**
     * 获取流程列表
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "process/list", method = RequestMethod.POST)
    @RequiresPermissions({"message:list"})
    public R list(@RequestParam Map<String, Object> params) {
        params.put("companyId", getUser().getCompanyId());
        PageUtils page = this.workflowMessageService.queryPage(params);

        return R.ok().put("page", page);
    }

    @ResponseBody
    @RequestMapping(value = "process/save", method = RequestMethod.POST)
    @RequiresPermissions(value = {"message:add", "message:update"}, logical = Logical.OR)
    public R save(@RequestBody YwWorkflowMessage workflowMessage) {
      /*  processMessage.setCompanyId(getCompanyOrCommunityByType("1"));
        this.workflowMessageService.save(processMessage);*/
        SysUserEntity user = getUser();
        workflowMessage.setCompanyId(user.getCompanyId());
        workflowMessageService.save(workflowMessage);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping(value = "getProcess/{id}", method = RequestMethod.GET)
    @RequiresPermissions({"message:info"})
    public R getProcess(@PathVariable("id") Long id) {
        return R.ok().put("process", this.workflowMessageService.getById(id));
    }

    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @RequiresPermissions({"message:delete"})
    public R getProcess(@RequestBody Long[] ids) {
        this.workflowMessageService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}