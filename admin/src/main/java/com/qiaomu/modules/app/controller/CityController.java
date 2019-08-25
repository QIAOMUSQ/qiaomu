package com.qiaomu.modules.app.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.sys.entity.SysDeptEntity;
import com.qiaomu.modules.sys.service.CityService;
import com.qiaomu.modules.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先（作廢）
 * @description: 获取社区和城市Controller
 * @Date 2018-11-28 21:41
 */
@Controller
@RequestMapping(value = "mobile/community")
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private SysDeptService sysDeptService;

    /**
     *  获取社区信息
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public R getCommunityList(String name) {
        List<SysDeptEntity> deptList = new ArrayList<>();
        try {
            if (name != null && name != "") {
                SysDeptEntity entity = new SysDeptEntity();
                entity.setName(name);
                Map<String, Object> params = new HashMap<>();
                params.put("name", name);
                deptList = sysDeptService.selectList(new EntityWrapper<SysDeptEntity>().like("name", name));
            } else {
                deptList = sysDeptService.selectList(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.ok("success", JSON.toJSON(deptList));
    }

    @ResponseBody
    @RequestMapping(value = "getCity", method = RequestMethod.POST)
    public R getCity() {
        return R.ok("success", JSON.toJSON(cityService.selectList(null)));
    }

}
