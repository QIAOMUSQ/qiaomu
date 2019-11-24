package com.qiaomu.modules.app.controller;

/**
 * Created by wenglei on 2019/7/6.
 */


import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.app.busssiness.UpdateAppBussinessImp;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
public class UpdateApp {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UpdateAppBussinessImp updateAppBussiness;

    /**
     * 列表
     */
    @RequestMapping("/upgrade/uploadApp")
    public String uploadApp(HttpServletRequest request){
        Map<String,Object> result = updateAppBussiness.uploadFile(request);

        return JSON.toJSONString(result);
    }

    @RequestMapping("/mobile/isNeedUpdate")
    public String isNeedUpdate(String version,String clientType){
        Map<String,Object> result = updateAppBussiness.isNeedUpdate(version,clientType);

        return JSON.toJSONString(result);
    }
    @RequestMapping("/mobile/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request){
        try {
            return updateAppBussiness.fileDownLoad(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpHeaders headers=new HttpHeaders();//设置响应头
        headers.add("Content-Disposition", "attachment;filename=erro");
        HttpStatus statusCode = HttpStatus.NOT_FOUND;//设置响应吗
        byte[] body= JSON.toJSONString(BuildResponse.fail()).getBytes();
        return new ResponseEntity<byte[]>(body,headers, statusCode);
    }

    /**
     * 找回密码
     * @param phone
     * @param securityCode
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "outapp/findBackPassword",method = RequestMethod.POST)
    public Object findBackPassword(String phone,String securityCode){
        try {
            if(securityCode.equals("666666")){
                return BuildResponse.success(JSON.toJSON(sysUserService.findBackPassword(phone,securityCode)));
            }else {
                return BuildResponse.fail("验证码错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            return BuildResponse.fail();
        }

    }

    /**
     * 重设密码
     * @param password
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "outapp/reSettingPassword",method = RequestMethod.POST)
    public Object reSettingPassword(String password,Long userId){
        try {
            SysUserEntity user = sysUserService.queryById(userId);
            user.setPassword(ShiroUtils.sha256(password, user.getSalt()));
            sysUserService.reSetPassword(user);
            return BuildResponse.success();
        }catch (Exception e){
            e.printStackTrace();
            return BuildResponse.fail();
        }
    }
}
