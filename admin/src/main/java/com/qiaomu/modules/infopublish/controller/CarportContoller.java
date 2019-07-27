package com.qiaomu.modules.infopublish.controller;

import com.qiaomu.modules.infopublish.service.CarportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public Object getPage(){
        return null;
    }
}
