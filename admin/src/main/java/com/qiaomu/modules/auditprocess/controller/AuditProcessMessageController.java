package com.qiaomu.modules.auditprocess.controller;

import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.auditprocess.entity.YwAuditProcessMessage;
import com.qiaomu.modules.auditprocess.service.YwAuditProcessMessageService;
import com.qiaomu.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 15:57
 */
@Controller
@RequestMapping({"processMessage"})
public class AuditProcessMessageController extends AbstractController
{

    @Autowired
    private YwAuditProcessMessageService processMessageService;

    @ResponseBody
    @RequestMapping(value={"process/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @RequiresPermissions({"message:list"})
    public R list(@RequestParam Map<String, Object> params)
    {
        params.put("companyId", getCompanyOrCommunityByType("1"));
        PageUtils page = this.processMessageService.queryPage(params);

        return R.ok().put("page", page); }
    @ResponseBody
    @RequestMapping(value={"process/save"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @RequiresPermissions(value={"message:add", "message:update"}, logical= Logical.OR)
    public R save(@RequestBody YwAuditProcessMessage processMessage) { processMessage.setCompanyId(getCompanyOrCommunityByType("1"));
        this.processMessageService.save(processMessage);
        return R.ok(); }
    @ResponseBody
    @RequestMapping(value={"getProcess/{id}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    @RequiresPermissions({"message:info"})
    public R getProcess(@PathVariable("id") Long id) { return R.ok().put("process", this.processMessageService.getById(id)); }
    @ResponseBody
    @RequestMapping(value={"delete"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @RequiresPermissions({"message:delete"})
    public R getProcess(@RequestBody Long[] ids) { this.processMessageService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}