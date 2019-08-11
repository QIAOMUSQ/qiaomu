package com.qiaomu.modules.propertycompany.controller;

import com.qiaomu.modules.propertycompany.service.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-10 16:12
 */
@RestController
@RequestMapping("mobile/advertise")
public class AdvertiseController {

    @Autowired
    private AdvertiseService advertiseService;



}
