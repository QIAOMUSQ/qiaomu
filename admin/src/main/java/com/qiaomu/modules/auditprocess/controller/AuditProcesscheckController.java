package com.qiaomu.modules.auditprocess.controller;

import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.auditprocess.service.YwAuditProcessCheckService;
import com.qiaomu.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 15:58
 */
@Controller
@RequestMapping({"processCheck"})
public class AuditProcesscheckController extends AbstractController {

    @Autowired
    private YwAuditProcessCheckService processCheckService;

    @ResponseBody
    @RequestMapping(value = {"process/list"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @RequiresPermissions({"process:check:list"})
    public R list(@RequestParam Map<String, Object> params) {
        params.put("companyId", getCompanyOrCommunityByType("1"));
        PageUtils page = this.processCheckService.queryPage(params);

        return R.ok().put("page", page);
    }
}