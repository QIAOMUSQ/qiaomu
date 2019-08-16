package com.qiaomu.modules.propertycompany.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.sys.controller.AbstractController;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description: 物业社区管理
 * @Date 2019-03-25 19:32
 */
@Controller
@RequestMapping(value = "communityMessage")
public class CommunityController extends AbstractController {

    @Autowired
    private YwCommunityService communityService;


    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions({"community:list"})
    public R getCommunityList(@RequestParam Map<String, Object> params, ServletRequest request) {
        //params.put("companyId", getCompanyOrCommunityByType("1"));
        SysUserEntity user = getUser();
        if(user.getCompanyId()!= null ){
            params.put("companyId",user.getCompanyId());
        }
        PageUtils page = communityService.queryPage(params);
        return R.ok().put("page", page);
    }

    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = {"community:save", "community:update"},logical = Logical.OR)
    public R save(@RequestBody YwCommunity community) {
        //对社区进行分类
        //community.setCompanyId(getCompanyOrCommunityByType("1"));
        SysUserEntity user = getUser();
        community.setCompanyId(user.getCompanyId());
        communityService.save(community);
        return R.ok();
    }

    /**
     *  根据社区id获取社区信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getCommunityById/{id}")
    public R getCommunityById(@PathVariable("id") Long id) {
        return R.ok("community", communityService.queryById(id));
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public R delete(Long id) {
        communityService.deleteById(id);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping(value = "getAllCommunity",method = RequestMethod.GET)
    public R getAllCommunity(Long companyId){
        YwCommunity community = new YwCommunity();
        if (companyId!=null){
            community.setCompanyId(companyId);
        }
        return R.ok().put("data", JSON.toJSON(communityService.findAllByCondition(community)));
    }

}
