package com.qiaomu.modules.propertycompany.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.propertycompany.entity.Advertise;
import com.qiaomu.modules.propertycompany.entity.AdvertiseBrowse;
import com.qiaomu.modules.propertycompany.service.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description: 广告
 * @Date 2019-08-10 16:12
 */
@Controller
@RequestMapping("mobile/advertise")
public class AdvertiseController {

    @Autowired
    private AdvertiseService advertiseService;


    @ResponseBody
    @RequestMapping(value = "pageList",method = RequestMethod.POST)
    public R pageList(@RequestParam Map<String, Object> params, ServletRequest request){
        Advertise advertise = new Advertise();
        if (params.get("merchantName") != null){
            advertise.setName((String) params.get("merchantName"));
        }
        PageUtils page = advertiseService.pageList(params,advertise);

        return R.ok().put("page",page);

    }

    @RequestMapping(value = "add")
    public String add(){
        return "modules/advertise/add";
    }

    @ResponseBody
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public R save(Advertise advertise, HttpServletRequest request){
       try {
           boolean ok = advertiseService.save(advertise,request);
           return R.ok().put("data",ok);
       }catch (Exception e){
           e.printStackTrace();
           return R.ok().put("data",false);
       }

    }

    @ResponseBody
    @RequestMapping(value = "getAdvertiseByCommunity",method = RequestMethod.POST)
    public Object getAdvertiseByCommunity(Long communityId){
       return BuildResponse.success(JSON.toJSON(advertiseService.getAdvertiseByCommunity(communityId)));
    }

    @ResponseBody
    @RequestMapping(value = "getAdvertiseById", method = RequestMethod.POST)
    public Object getAdvertiseById(Long id) {
        Advertise advertise = advertiseService.selectById(id);
        return BuildResponse.success(JSON.toJSON(advertise));
    }

    @ResponseBody
    @RequestMapping(value = "getStatistics", method = RequestMethod.POST)
    public Object getStatistics(AdvertiseBrowse advertise) {
        return advertiseService.getStatistics(advertise);
    }

    @ResponseBody
    @RequestMapping(value = "getStatisticsDetail", method = RequestMethod.POST)
    public Object getStatisticsDetail(AdvertiseBrowse advertise) {
        return advertiseService.getStatisticsDetail(advertise);
    }

}
