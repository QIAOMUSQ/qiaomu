package com.qiaomu.modules.app.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.Enum.CommunityRoleType;
import com.qiaomu.common.utils.*;
import com.qiaomu.common.validator.Assert;
import com.qiaomu.modules.sys.controller.AbstractController;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.UserExtend;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.sys.service.UserExtendService;
import com.qiaomu.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.http.HttpHeaders.AUTHORIZATION;


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

    @Autowired
    private UserExtendService userExtendService;

    /**
     * 用户登陆
     * @param phone
     * @param password
     * @param isAgree
     * @return
     */
    @ResponseBody
    @RequestMapping(value ="login")
    public R login(String phone, String password, ServletRequest request, boolean isAgree,HttpServletResponse response) {
        try {
           if(!"".equals(phone) && phone != null){
               Subject subject = ShiroUtils.getSubject();
               UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
               subject.login(token);
               SysUserEntity userEntity = getUser();
               userEntity.setPassword("");
               userEntity.setSalt("");
               List<UserExtend> userExtends = userExtendService.findAll(new UserExtend(userEntity.getUserId()));
               for (UserExtend user: userExtends){
                   if (user.getCompanyRoleType().equals(CommunityRoleType.WORKER.getValue())){
                       userEntity.setCommunityRoleType(CommunityRoleType.WORKER.getName());
                       break;
                   }
               }
               String session  = response.getHeader(AUTHORIZATION);
               userEntity.setSessionId(session);
               return R.ok("success",JSON.toJSON(userEntity));
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
        }catch(DisabledAccountException e){
            logger.info(DateTime.now().toString("yyyy-MM-dd HH:mm:ss") +"phone:"+phone+"--"+e.getMessage());
            return R.ok("error",e.getMessage());
        }catch (Exception e) {
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
        SysUserEntity userEntity = sysUserService.isExist(phone);
        if (userEntity!=null) {
            return R.ok("error", "手机号码已存在");
        }
        List roleList = new ArrayList();
        roleList.add(5l);
        user.setRoleIdList(roleList);
        user.setDeptId(9l);
        user.setStatus(Integer.valueOf(1));
        user.setNickName(RandomName.randomName(true,4));
        user.setHandImgId(167l);
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
    public Object modifyPassword(Long userId,String password, String newPassword) {
        Assert.isBlank(newPassword, "新密码不为能空");
        SysUserEntity user = sysUserService.selectById(userId);
        password = ShiroUtils.sha256(password, user.getSalt());

        newPassword = ShiroUtils.sha256(newPassword, user.getSalt());

        boolean flag = this.sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return BuildResponse.fail("原密码不正确");
        }
        return BuildResponse.success();
    }

    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public R logout(){
        ShiroUtils.logout();
        return R.ok();
    }

    /**
     * 设置个人中心
     * @param userPhone 用户号码
     * @param nickName  昵称
     * @param sex   性别
     * @param imgId 上传图片ID
     * @param communityId
     * @return
     */
    @RequestMapping(value = "setPersonalCenter",method = RequestMethod.POST)
    public R setPersonalCenter(String userPhone,String nickName,String newPhone,String sex,Long imgId,Long communityId){
        String info = userExtendService.setPersonalCenter(userPhone,newPhone,nickName,sex,imgId,communityId);
        if(info.equals("success")){
            return R.ok(info,"保存成功");
        }else {
            return R.ok(info,"保存失败");
        }

    }

    /**
     * 根据用户ID 获取用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "getUserInfoByUserId")
    public Object getUserInfoById(Long userId){
        return BuildResponse.success(JSON.toJSON(sysUserService.selectById(userId)));
    }

    @RequestMapping(value = "setUserLoginCommunity")
    public Object setUserLoginCommunity(Long userId,Long communityId){
        sysUserService.setUserLoginCommunity(userId,communityId);
        return BuildResponse.success();
    }
}
