package com.qiaomu.modules.android.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.sys.service.ProvinceCityDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 13:31
 */
@Controller
@RequestMapping({"App/provinceCity"})
public class ProvinceCityDateContriller
{

    @Autowired
    private ProvinceCityDateService provinceCityDateService;

    @ResponseBody
    @RequestMapping(value={"getPrivateDate"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public R getProviceDate()
    {
        List provinceList = this.provinceCityDateService.getProvinceData();
        return R.ok("success", JSON.toJSON(provinceList));
    }
    @ResponseBody
    @RequestMapping(value={"getCityDateByProvinceName"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public R getCityDateByProvinceName(@RequestParam Map<String, Object> params) { List cityList = this.provinceCityDateService.getProvinceCityDate(params);
        return R.ok("success", JSON.toJSON(cityList)); }
    @ResponseBody
    @RequestMapping(value={"getCityDateByProvinceCode"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public R getCityDateByProvinceCode(@RequestParam Map<String, Object> params) {
        List cityList = this.provinceCityDateService.getProvinceCityDate(params);
        return R.ok("success", JSON.toJSON(cityList));
    }
}