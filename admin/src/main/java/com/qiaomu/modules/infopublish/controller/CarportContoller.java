package com.qiaomu.modules.infopublish.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.modules.infopublish.entity.CarportEntity;
import com.qiaomu.modules.infopublish.service.CarportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author 李品先
 * @description:
 * @Date 2019-07-27 0:16
 */
@RestController
@RequestMapping("mobile/carport")
public class CarportContoller {
    @Autowired
    private CarportService carportService;

    /**
     * 获取车辆出租信息
     * @param carport
     * @return
     */
    @RequestMapping("getAll")
    public Object getPage(CarportEntity carport){
         List<CarportEntity> data =  carportService.selectAll(carport);
        return BuildResponse.success(JSON.toJSON(data));
    }

    public Object save(){
        return "";
    }

}
