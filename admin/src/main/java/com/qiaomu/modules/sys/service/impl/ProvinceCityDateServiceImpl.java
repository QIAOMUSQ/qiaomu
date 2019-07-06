package com.qiaomu.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.qiaomu.modules.sys.dao.ProvinceCityDateDao;
import com.qiaomu.modules.sys.entity.ProvinceCityDateEntity;
import com.qiaomu.modules.sys.service.ProvinceCityDateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-01-22 9:41
 */
@Service
public class ProvinceCityDateServiceImpl extends ServiceImpl<ProvinceCityDateDao, ProvinceCityDateEntity> implements ProvinceCityDateService {

    public List<ProvinceCityDateEntity> getProvinceData() {
        return selectList(new EntityWrapper()
                .groupBy("PROVINCE_CODE"));
    }

    public List<ProvinceCityDateEntity> getProvinceCityDate(Map<String, Object> params) {
        String provinceName = (String) params.get("provinceName");
        String provinceCode = (String) params.get("provinceCode");
        String cityCode = (String) params.get("cityCode");
        String id = (String) params.get("id");
        if (id == null) id = "0";
        return selectList(new EntityWrapper()
                .eq(StringUtils.isNotEmpty(provinceName),
                        "PROVINCE_NAME", provinceName)
                .eq(StringUtils.isNotEmpty(provinceCode),
                        "PROVINCE_CODE", provinceCode)
                .eq(StringUtils.isNotEmpty(cityCode),
                        "CITY_CODE", cityCode)
                .eq(id != "0", "ID",
                        Long.valueOf(id)));
    }

    public List<ProvinceCityDateEntity> getCityDateByProvinceCode(String provinceCode) {
        return selectList(new EntityWrapper()
                .eq("PROVINCE_CODE", provinceCode));
    }

    public ProvinceCityDateEntity getProvCityByCityName(String cityName) {
        return (ProvinceCityDateEntity) selectOne(new EntityWrapper()
                .eq("CITY_NAME", cityName));
    }

    @Override
    public ProvinceCityDateEntity getProCityByCityCode(String cityCode) {
        return (ProvinceCityDateEntity) selectOne(new EntityWrapper()
                .eq("CITY_CODE", cityCode));
    }
}
