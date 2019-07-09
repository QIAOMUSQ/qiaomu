package com.qiaomu.modules.sys.controller;

import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.service.UserExtendService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 李品先
 * @description: 物业社区用户管理
 * @Date 2019-03-30 17:04
 */
@Controller
@RequestMapping(value = "communityUser")
public class UserExtendController extends AbstractController {

    @Autowired
    private UserExtendService userExtendService;

    @ResponseBody
    @RequestMapping(value = "people/list", method = RequestMethod.POST)
    @RequiresPermissions("process:people:list")
    public R list(@RequestParam Map<String, Object> params) {
        SysUserEntity user = getUser();
        if(user.getCompanyId() != null ){
            params.put("companyId", user.getCompanyId());
        }
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
     * @param  id 提交信息id
     * @param info      备注信息
     * @param type      1：通过 2：不通过 3：禁用
     * @param companyRoleType  物业角色
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveCheckInfo", method = RequestMethod.POST)
    @RequiresPermissions("process:people:check")
    public R saveCheckInfo(String info, String type, String companyRoleType,Long id) {

        userExtendService.saveCheckInfo(info, type, companyRoleType,id,getUserId());
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
