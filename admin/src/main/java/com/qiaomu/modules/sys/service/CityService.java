package com.qiaomu.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.modules.android.entity.City;

import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2018-11-28 21:37
 */
public interface CityService extends IService<City> {

    /**
     * 查询城市
     * @param map
     * @return
     */
    List<City> queryList(Map<String, Object> map);

}
