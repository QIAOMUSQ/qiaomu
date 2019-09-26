package com.qiaomu.modules.propertycompany.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.propertycompany.entity.YwPropertyCompany;
import com.qiaomu.modules.propertycompany.service.YwPropertyCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @author 李品先
 * @description: 物业公司管理
 * @Date 2019-04-21 16:15
 */
@Controller
@RequestMapping({"propertyCompanyManage"})
public class PropertyCompanyManageController {

    @Autowired
    private YwPropertyCompanyService propertyCompanyService;


    @ResponseBody
    @RequestMapping(value = "company/list", method = RequestMethod.POST)
    @RequiresPermissions({"company:list"})
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = this.propertyCompanyService.queryPage(params);
        return R.ok().put("page", page);
    }

    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions({"company:save"})
    public R addCompany(@RequestBody YwPropertyCompany company) {
        System.out.printf(JSON.toJSONString(company), new Object[0]);

        company.setCreateTime(new Date());
        this.propertyCompanyService.save(company);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping({"/info/{id}"})
    @RequiresPermissions({"company:info"})
    public R info(@PathVariable("id") Long id) {
        YwPropertyCompany company = (YwPropertyCompany) this.propertyCompanyService.selectById(id);

        return R.ok().put("company", company);
    }

    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @RequiresPermissions({"company:update"})
    public R update(@RequestBody YwPropertyCompany company) {
        this.propertyCompanyService.update(company);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping(value = "delete", method = {RequestMethod.POST})
    @RequiresPermissions({"company:update"})
    public R delete(@RequestBody Long[] ids) {
        propertyCompanyService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}