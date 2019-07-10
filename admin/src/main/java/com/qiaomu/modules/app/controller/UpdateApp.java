package com.qiaomu.modules.app.controller;

/**
 * Created by wenglei on 2019/7/6.
 */


import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.app.busssiness.UpdateAppBussinessImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
public class UpdateApp {
    @Autowired
    private UpdateAppBussinessImp updateAppBussiness;

    /**
     * 列表
     */
    @RequestMapping("/uploadApp")
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

}
