package com.qiaomu.modules.sys.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.AESUtil;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.modules.propertycompany.service.LoginStatisticsService;
import com.qiaomu.modules.welfare.model.PointRankForm;
import com.qiaomu.modules.welfare.service.PublicWelfareTaskService;
import com.qiaomu.modules.workflow.entity.RepairsInfo;
import com.qiaomu.modules.workflow.service.RepairsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wenglei on 2019/11/25.
 */
@RestController
@RequestMapping(value = "/sys/dashBoard")
public class DashboardController  extends AbstractController{
    @Autowired
    private PublicWelfareTaskService publicWelfareTaskService;
    @Autowired
    private LoginStatisticsService loginStatisticsService;
    @Autowired
    private RepairsInfoService repairsInfoService;

    @RequestMapping(value = "statisticLoveGoldByCommunityId",method = RequestMethod.POST)
    public String statisticLoveGoldByCommunityId(String communityId){
        List<PointRankForm> result = publicWelfareTaskService.selectTopPointUser(communityId);
        for(PointRankForm pointRankForm:result) {
            if(pointRankForm.getRealName()!=null) {
                pointRankForm.setRealName(AESUtil.decrypt(pointRankForm.getRealName()));
            }
        }
        return JSON.toJSONString(BuildResponse.success(result));

    }

    @RequestMapping(value = "loginTimesByCommunityId",method = RequestMethod.POST)
    public String loginTimesByCommunityId(String communityId){
        int times = loginStatisticsService.statisticsLoginByCommunityId(communityId);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("logintime",times);

        return JSON.toJSONString(BuildResponse.success(result));

    }

    @RequestMapping(value = "loginTimesByCommunityId",method = RequestMethod.POST)
    public String loginTimesTodayByCommunityId(String communityId){
        int times = loginStatisticsService.loginTimesTodayByCommunityId(communityId);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("logintime",times);

        return JSON.toJSONString(BuildResponse.success(result));

    }
    @RequestMapping(value = "staticRepairsByStatus",method = RequestMethod.POST)
    public String staticRepairsByStatus(String communityId){
        List<HashMap<String,String>> result = repairsInfoService.staticRepairsByStatus(communityId);
        return JSON.toJSONString(BuildResponse.success(result));
    }

    @RequestMapping(value = "staticRepairsByrepairsType",method = RequestMethod.POST)
    public String staticRepairsByrepairsType(String communityId){
        List<HashMap<String,String>> result = repairsInfoService.staticRepairsByrepairsType(communityId);
        return JSON.toJSONString(BuildResponse.success(result));
    }
    @RequestMapping(value = "staticRepairsByAssign",method = RequestMethod.POST)
    public String staticRepairsByAssign(String communityId){
        List<RepairsInfo> result = repairsInfoService.staticRepairsByAssign(communityId);
        return JSON.toJSONString(BuildResponse.success(result));
    }

}
