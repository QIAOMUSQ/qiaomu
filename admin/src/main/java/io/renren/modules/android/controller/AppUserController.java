package io.renren.modules.android.controller;

import io.renren.common.utils.AESUtil;
import io.renren.common.utils.R;
import io.renren.common.utils.SecretUtils;
import io.renren.common.validator.Assert;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.modules.android.service.AppUserService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author 李品先
 * @description:App中用户管理
 * @Date 2018-10-23 20:02
 */
@RestController
@RequestMapping("/App/user")
public class AppUserController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(AppUserController.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private AppUserService appUserService;

   // @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R login(String username,String password){

        try{
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
        }catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return R.error("账号或密码不正确");
        }catch (LockedAccountException e) {
            return R.error("账号已被锁定,请联系管理员");
        }catch (AuthenticationException e) {
            log.error("登陆失败----------用户:"+username,e);
            return R.error("账户登陆失败");
        }catch (Exception e){
            log.error("登陆失败----------用户:"+username,e);
            return R.error("账户登陆失败");
        }

        return R.ok();
    }

    /**
     * 新增用户
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public R registUser(String phone,String password,Integer communityId){

        appUserService.save(phone,password,communityId);
        return R.ok();
    }

    @RequestMapping("/modifyPassword")
    public R modifyPassword(String username,String password, String newPassword){
        Assert.isBlank(newPassword, "新密码不为能空");

        //原密码
        password = ShiroUtils.sha256(AESUtil.decrypt(password), getUser().getSalt());
        //新密码
        newPassword = ShiroUtils.sha256(AESUtil.decrypt(newPassword), getUser().getSalt());

        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if(!flag){
            return R.error("原密码不正确");
        }

        return R.ok();
    }


}
