package com.qiaomu.modules.propertycompany.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.modules.propertycompany.entity.LoginStatistics;
import com.qiaomu.modules.propertycompany.service.LoginStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-25 19:29
 */
@Controller
@RequestMapping("Statistics")
public class LoginStatisticsController {

    @Autowired
    private LoginStatisticsService loginStatisticsService;

    @ResponseBody
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Object getStatisticsDate(LoginStatistics condition){
        List<LoginStatistics> data = loginStatisticsService.getStatisticsDate(condition);
        return JSON.toJSON(data);
    }
    @ResponseBody
    @RequestMapping(value = "loginByDate",method = RequestMethod.POST)
    public Object loginByDate(LoginStatistics condition){
        List<LoginStatistics> data = loginStatisticsService.getStatisticsloginByDate(condition);
        return JSON.toJSON(data);
    }

}
