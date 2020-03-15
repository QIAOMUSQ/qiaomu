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
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.qiaomu.common.utils.Constant.OUT_DIR;
import static com.qiaomu.common.utils.Constant.SERVER_URL;


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
     * 设置静态文件
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "outapp/addStaticFile",method = RequestMethod.POST)
    public Object addStaticFile(String content,String fileName){
        String urlPath=SERVER_URL+"/outapp/image/"+fileName+".txt";
        try{
            String savePath =OUT_DIR+"image/";
            File file =new File(savePath+fileName+".txt");

            if(!file.exists()){
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            osw.write(content);
            osw.flush();

        }catch(IOException e){
            e.printStackTrace();
        }

        Map<String,String> url = new HashMap<>();
        url.put("url",urlPath);
        return BuildResponse.success(url);
    }

    /**
     * 读取静态文件
     * @param fileName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "outapp/readStaticFile",method = RequestMethod.POST)
    public Object readStaticFile(String fileName){
        String fileContent="";

        try{
            String savePath =OUT_DIR+"image/";
            File file =new File(savePath+fileName+".txt");

            if(!file.exists()){
                return BuildResponse.fail();
            }

            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                  fileContent += line;
                  fileContent += "\r\n"; // 补上换行符   
            }


        }catch(IOException e){
            e.printStackTrace();
        }

        Map<String,String> url = new HashMap<>();
        url.put("fileContent",fileContent);
        return BuildResponse.success(url);
    }



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
