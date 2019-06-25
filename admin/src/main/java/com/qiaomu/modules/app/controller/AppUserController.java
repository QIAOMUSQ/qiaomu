package com.qiaomu.modules.app.controller;

import com.qiaomu.common.utils.AESUtil;
import com.qiaomu.common.utils.DicRoleDeptCode;
import com.qiaomu.common.utils.R;
import com.qiaomu.common.validator.Assert;
import com.qiaomu.modules.sys.controller.AbstractController;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author
 * @description:App
 * @Date 2018-10-23 20:02
 */
@RestController
@RequestMapping("mobile/user")
public class AppUserController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(AppUserController.class);

    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户登陆
     * @param phone
     * @param password
     * @param isAgree
     * @return
     */
    @ResponseBody
    @RequestMapping(value ="login")
    public R login(String phone,String password,boolean isAgree) {
        try {
           if(!"".equals(phone) && phone != null){
               Subject subject = ShiroUtils.getSubject();
               UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
               subject.login(token);
               return R.ok();
           }else {
               return R.ok("error","客户端出现问题");
           }
        } catch (UnknownAccountException e) {
            log.error(DateTime.now().toString("yyyy-MM-dd HH:mm:ss") +"phone:"+phone+"--"+e.getMessage());
            return R.ok("error",e.getMessage());
        } catch (IncorrectCredentialsException e) {
            log.error(DateTime.now().toString("yyyy-MM-dd HH:mm:ss") +"phone:"+phone+"--"+e.getMessage());
            return R.ok("error","账号或密码不正确");
        } catch (LockedAccountException e) {
            log.error(DateTime.now().toString("yyyy-MM-dd HH:mm:ss") +"phone:"+phone+"--"+e.getMessage());
            return R.ok("error","账号已被锁定");
        } catch (Exception e) {
            log.error(DateTime.now().toString("yyyy-MM-dd HH:mm:ss") +"phone:"+phone+"--"+e.getMessage());
            return R.ok("error","登陆失败");
        }

    }

    /**
     * 用户注册
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public R registerUser(String phone, String password) {
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


    /**
     * 修改密码
     * @param password
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "modifyPassword",method = RequestMethod.POST)
    public R modifyPassword(String password, String newPassword) {
        Assert.isBlank(newPassword, "新密码不为能空");

        password = ShiroUtils.sha256(AESUtil.decrypt(password), getUser().getSalt());

        newPassword = ShiroUtils.sha256(AESUtil.decrypt(newPassword), getUser().getSalt());

        boolean flag = this.sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return R.ok("error","原密码不正确");
        }

        return R.ok();
    }

    @RequestMapping(value = "logout",method = RequestMethod.POST)
    public R logout(){
        ShiroUtils.logout();
        return R.ok();
    }

}
