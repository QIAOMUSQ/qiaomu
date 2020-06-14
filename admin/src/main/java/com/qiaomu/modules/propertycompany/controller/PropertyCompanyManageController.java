package com.qiaomu.modules.propertycompany.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.propertycompany.entity.YwPropertyCompany;
import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import com.qiaomu.modules.propertycompany.service.YwPropertyCompanyService;
import com.qiaomu.modules.sys.entity.UserExtend;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.sys.service.UserExtendService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Autowired
    private YwCommunityService communityService;

    @Autowired
    private UserExtendService userExtendService;


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
        YwPropertyCompany company =  this.propertyCompanyService.selectById(id);

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

    @ResponseBody
    @RequestMapping(value = "findCompanyByUserId", method = {RequestMethod.POST})
    public R findCompanyByUserName(Long userId){
        YwPropertyCompany company =  propertyCompanyService.findCompanyByUserId(userId);
        if (company==null){
            EntityWrapper<UserExtend> userWrapper = new EntityWrapper<>();
            userWrapper.eq("user_id",userId);
            List<UserExtend> extendList =  userExtendService.selectList(userWrapper);
            EntityWrapper<YwCommunity> wrapper = new EntityWrapper<>();
            List<Long> ids = new ArrayList<>();
            for (UserExtend extend:extendList){
                ids.add(extend.getId());
            }
            wrapper.in("admin_id",ids);
            List<YwCommunity> communityList =  communityService.selectList(wrapper);
            return R.ok().put("result",JSON.toJSON(communityList));
        }
        return R.ok().put("result",JSON.toJSON(company));
    }
}