package com.qiaomu.modules.sys.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.AESUtil;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.modules.propertycompany.service.LoginStatisticsService;
import com.qiaomu.modules.welfare.model.PointRankForm;
import com.qiaomu.modules.welfare.service.PublicWelfareTaskService;
import com.qiaomu.modules.workflow.entity.RepairsInfo;
import com.qiaomu.modules.workflow.service.RepairsInfoService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @RequestMapping(value = "loginTimesTodayByCommunityId",method = RequestMethod.POST)
    public String loginTimesTodayByCommunityId(String communityId){
        int times = loginStatisticsService.loginTimesTodayByCommunityId(communityId);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("logintime",times);

        return JSON.toJSONString(BuildResponse.success(result));

    }
    @RequestMapping(value = "staticRepairsByStatus",method = RequestMethod.POST)
    public String staticRepairsByStatus(Long communityId,String startTime,String endTime ){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        RepairsInfo info = new RepairsInfo();
        try {
            info.setStartTime(sdf.parse(startTime));
            info.setEndTime(sdf.parse(endTime));
            info.setCommunityId(communityId);
            List<HashMap<String,String>> result = repairsInfoService.staticRepairsByStatus(info);
            return JSON.toJSONString(BuildResponse.success(result));
        } catch (ParseException e) {
            e.printStackTrace();
            return JSON.toJSONString(BuildResponse.fail());
        }


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

    @RequestMapping(value = "getCompanyRepairStatistic",method = RequestMethod.POST)
    public String getCompanyRepairStatistic(Long companyId){
        List<RepairsInfo> result = new ArrayList<>();
        Map<String, List<RepairsInfo>> infoMap = new HashMap<>();
        try {
            RepairsInfo info = new RepairsInfo();
            String date = DateTime.now().toString("yyyy-MM-dd 00:00:00");
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            info.setEndTime(new Date());
            info.setCompanyId(companyId);
            info.setStartTime(sdf.parse(date));
            result = repairsInfoService.getCompanyRepairStatistic(info);

            result.forEach(data->{
                if (infoMap.containsKey(data.getCommunityName())){
                    infoMap.get(data.getCommunityName()).add(data);
                }else {
                    List<RepairsInfo> repairs= new ArrayList<>();
                    repairs.add(data);
                    infoMap.put(data.getCommunityName(),repairs);
                }
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(BuildResponse.success(infoMap));
    }

    @RequestMapping(value = "getRepairInfoByTime",method = RequestMethod.POST)
    public Object getRepairInfoByTime(String startTime,String endTime,Long communityId){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        RepairsInfo info = new RepairsInfo();
        try {
            Map<String,List<RepairsInfo>> map = new HashMap<>();
            info.setStartTime(sdf.parse(startTime));
            info.setEndTime(sdf.parse(endTime));
            info.setCommunityId(communityId);
            List<RepairsInfo> infoList = repairsInfoService.getRepairInfoByCondition(info);
            infoList.forEach(d->{
                if (map.containsKey(d.getRepairsType())){
                    map.get(d.getRepairsType()).add(d);
                }else {
                    List<RepairsInfo> l = new ArrayList<>();
                    l.add(d);
                    map.put(d.getRepairsType(),l);
                }
            });
            return BuildResponse.success(map);
        } catch (ParseException e) {
            e.printStackTrace();
            return BuildResponse.fail();
        }
    }

}
