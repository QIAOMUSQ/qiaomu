package com.qiaomu.modules.sys.service;


import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.modules.sys.entity.ProvinceCityDateEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-01-22 9:40
 */
public interface ProvinceCityDateService extends IService<ProvinceCityDateEntity> {
    /**
     * 获取省份数据
     *
     * @return
     */
    List<ProvinceCityDateEntity> getProvinceData();

    /**
     * 根据省份获取城市数据
     *
     * @param params
     * @return
     */
    List<ProvinceCityDateEntity> getProvinceCityDate(Map<String, Object> params);

    List<ProvinceCityDateEntity> getCityDateByProvinceCode(String provinceCode);

    ProvinceCityDateEntity getProvCityByCityName(String cityName);
}
