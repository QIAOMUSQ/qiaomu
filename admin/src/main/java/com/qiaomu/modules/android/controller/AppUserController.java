package com.qiaomu.modules.android.controller;

import com.qiaomu.common.utils.AESUtil;
import com.qiaomu.common.utils.DicRoleDeptCode;
import com.qiaomu.common.utils.R;
import com.qiaomu.common.validator.Assert;
import com.qiaomu.modules.sys.controller.AbstractController;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.shiro.ShiroUtils;
import com.qiaomu.modules.android.service.AppUserService;
import com.qiaomu.modules.sys.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * @author
 * @description:App
 * @Date 2018-10-23 20:02
 */
@RestController
@RequestMapping("/App/user")
public class AppUserController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(AppUserController.class);

    @Autowired
    private SysUserService sysUserService;

    @ResponseBody
    @RequestMapping(value={"/login"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public R login(String username, String password) { try { Subject subject = ShiroUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
    } catch (UnknownAccountException e) {
        return R.error(e.getMessage());
    } catch (IncorrectCredentialsException e) {
        return R.error("账号或密码不正确");
    } catch (LockedAccountException e) {
        return R.error("账号已被锁定,请联系管理员");
    } catch (AuthenticationException e) {
        log.error("登陆失败----------用户:" + username, e);
        return R.error("账户登陆失败");
    } catch (Exception e) {
        log.error("登陆失败----------用户:" + username, e);
        return R.error("账户登陆失败");
    }

        return R.ok();
    }

    @RequestMapping(value={"/regist"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public R registUser(String phone, String password)
    {
        SysUserEntity user = new SysUserEntity();
        user.setUsername(phone);
        user.setPassword(password);

        if (this.sysUserService.isExist(phone)) {
            return R.ok("error", "手机号码已存在");
        }

        user.setPropertyCompanyRoleType("4");
        String[] role_dept = ((String) DicRoleDeptCode.role_dept_map.get("4")).split("_");
        List roleList = new ArrayList();
        roleList.add(Long.valueOf(role_dept[0]));
        user.setRoleIdList(roleList);
        user.setDeptId(Long.valueOf(role_dept[1]));
        user.setStatus(Integer.valueOf(1));
        this.sysUserService.save(user);
        return R.ok();
    }

    @RequestMapping({"/modifyPassword"})
    public R modifyPassword(String username, String password, String newPassword)
    {
        Assert.isBlank(newPassword, "新密码不为能空");

        password = ShiroUtils.sha256(AESUtil.decrypt(password), getUser().getSalt());

        newPassword = ShiroUtils.sha256(AESUtil.decrypt(newPassword), getUser().getSalt());

        boolean flag = this.sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return R.error("原密码不正确");
        }

        return R.ok();
    }


}
