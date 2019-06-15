package com.qiaomu.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.Constant;
import com.qiaomu.modules.app.dao.CityDao;
import com.qiaomu.modules.app.entity.City;

import com.qiaomu.modules.sys.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2018-11-28 21:38
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityDao, City> implements CityService {


    @Override
    public List<City> queryList(Map<String, Object> params) {
        List<City> cityList =
                this.selectList(new EntityWrapper<City>()
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null,
                                (String) params.get(Constant.SQL_FILTER)));
        return cityList;
    }
}
