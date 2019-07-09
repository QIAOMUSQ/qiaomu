package com.qiaomu.modules.app.controller;

/**
 * Created by wenglei on 2019/7/6.
 */


import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.app.busssiness.UpdateAppBussinessImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping("upgrade")
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

    @RequestMapping("/isNeedUpdate")
    public String isNeedUpdate(String version,String clientType){
        Map<String,Object> result = updateAppBussiness.isNeedUpdate(version,clientType);

        return JSON.toJSONString(result);
    }

}
