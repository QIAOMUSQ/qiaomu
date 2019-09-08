package com.qiaomu.modules.propertycompany.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.propertycompany.entity.LoginStatistics;
import com.qiaomu.modules.propertycompany.service.LoginStatisticsService;
import com.qiaomu.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-25 19:29
 */
@Controller
@RequestMapping("Statistics")
public class LoginStatisticsController  extends AbstractController{

    @Autowired
    private LoginStatisticsService loginStatisticsService;

    @ResponseBody
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Object getStatisticsDate(LoginStatistics condition){
        Long companyId = getCompanyId();
        if (companyId!=null)condition.setCompanyId(companyId);
        List<LoginStatistics> data = loginStatisticsService.getStatisticsDate(condition);
        return JSON.toJSON(data);
    }
    @ResponseBody
    @RequestMapping(value = "loginByDate",method = RequestMethod.POST)
    public Object loginByDate(@RequestParam Map<String, Object> params){
        Long companyId = getCompanyId();
        params.put("companyId",companyId);
        PageUtils page = loginStatisticsService.pageList(params);
        return R.ok().put("page",page);
    }

}
