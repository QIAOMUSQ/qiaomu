package com.qiaomu.modules.sys.controller;

import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.sys.service.YwUserExtendService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 李品先
 * @description: 物业社区用户
 * @Date 2019-03-30 17:04
 */
@Controller
@RequestMapping(value = "communityUser")
public class YwUserExtendController extends AbstractController {

    @Autowired
    private YwUserExtendService userExtendService;

    @ResponseBody
    @RequestMapping(value = "people/list", method = RequestMethod.POST)
    @RequiresPermissions("process:people:list")
    public R list(@RequestParam Map<String, Object> params) {
        params.put("companyId", getCompanyOrCommunityByType("1"));
        PageUtils page = userExtendService.queryPage(params);

        return R.ok().put("page", page);
    }

    @ResponseBody
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @RequiresPermissions("process:people:info")
    public R getUserExtendInfo(@PathVariable("id") Long id) {
        return R.ok().put("userExtendInfo", userExtendService.getUserExtendInfo(id));
    }

    /**
     * 用户审核
     *
     * @param userPhone 手机号码
     * @param info      备注信息
     * @param type      1：通过 2：不通过 3：禁用
     * @param roleType  物业角色
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveCheckInfo", method = RequestMethod.POST)
    @RequiresPermissions("process:people:check")
    public R saveCheckInfo(String userPhone, String info, String type, String roleType) {
        userExtendService.saveCheckInfo(userPhone, info, type, roleType);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @RequiresPermissions("process:people:check")
    public R saveCheckInfo(@RequestBody Long[] userIds) {
        userExtendService.delect(userIds);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping(value = "getUserCompanyId", method = RequestMethod.GET)
    public R getUserCompanyId() {
        return R.ok().put("id", getCompanyOrCommunityByType("1"));
    }
}
