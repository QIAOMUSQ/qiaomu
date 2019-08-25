package com.qiaomu.modules.propertycompany.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.modules.propertycompany.dao.LoginStatisticsDao;
import com.qiaomu.modules.propertycompany.entity.LoginStatistics;
import com.qiaomu.modules.propertycompany.service.LoginStatisticsService;
import com.qiaomu.modules.sys.entity.ProvinceCityDateEntity;
import com.qiaomu.modules.sys.service.ProvinceCityDateService;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-24 16:28
 */
@Service
public class LoginStatisticsServiceImpl extends ServiceImpl<LoginStatisticsDao,LoginStatistics> implements LoginStatisticsService {

    @Autowired
    private ProvinceCityDateService provinceCityDateService;
    @Override
    public List<LoginStatistics> getStatisticsDate(LoginStatistics condition) {

        ProvinceCityDateEntity cityDate  = provinceCityDateService.getProCityByCityCode(condition.getCityCode());
        condition.setCityId(cityDate.getId());
        return this.baseMapper.getStatisticsDate(condition);
    }

    @Override
    public List<LoginStatistics> getStatisticsloginByDate(LoginStatistics condition) {
        ProvinceCityDateEntity cityDate  = provinceCityDateService.getProCityByCityCode(condition.getCityCode());
        condition.setCityId(cityDate.getId());
        return this.baseMapper.getStatisticsLoginByDate(condition);
    }
}
