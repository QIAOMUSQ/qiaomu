package com.qiaomu.modules.propertycompany.controller;

import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.sys.controller.AbstractController;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.sys.service.YwCommunityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-03-25 19:32
 */
@Controller
@RequestMapping(value = "communityMessage")
public class CommunityController extends AbstractController {

    @Autowired
    private YwCommunityService communityService;



    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @RequiresPermissions("community:list")
    public R getCommunityList(@RequestParam Map<String, Object> params, ServletRequest request){
        params.put("companyId",getCompanyOrCommunityByType("1"));
        PageUtils page = communityService.queryPage(params);
        return R.ok().put("page", page);
    }

    @ResponseBody
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @RequiresPermissions({"community:save","community:update"})
    public R save(@RequestBody YwCommunity community){
         //对社区进行分类
        community.setCompanyId(getCompanyOrCommunityByType("1"));
        communityService.save(community);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping(value = "getCommunityById/{id}")
    public R getCommunityById(@PathVariable("id") Long id){
        return R.ok("community", communityService.queryById(id));
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public R delete(Long id){
        communityService.deleteById(id);
        return R.ok();
    }


}
