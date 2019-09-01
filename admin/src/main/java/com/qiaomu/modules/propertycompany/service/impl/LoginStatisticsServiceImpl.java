package com.qiaomu.modules.propertycompany.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.propertycompany.dao.LoginStatisticsDao;
import com.qiaomu.modules.propertycompany.entity.LoginStatistics;
import com.qiaomu.modules.propertycompany.service.LoginStatisticsService;
import com.qiaomu.modules.sys.entity.ProvinceCityDateEntity;
import com.qiaomu.modules.sys.service.ProvinceCityDateService;
import io.swagger.annotations.Authorization;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        condition.setCityCode(condition.getCityCode());
        return this.baseMapper.getStatisticsDate(condition);
    }

    @Override
    public List<LoginStatistics> getStatisticsloginByDate(LoginStatistics condition) {
        ProvinceCityDateEntity cityDate  = provinceCityDateService.getProCityByCityCode(condition.getCityCode());
        condition.setCityId(cityDate.getId());
        return this.baseMapper.getStatisticsLoginByDate(condition);
    }

    @Override
    public PageUtils pageList(Map<String, Object> params) {
        Page<LoginStatistics> page = new Query(params).getPage();// 当前页，总条
        LoginStatistics condition = new LoginStatistics();
        if (StringUtils.isNotBlank((String) params.get("cityCode"))){
            condition.setCityCode((String) params.get("cityCode"));
        }
        if (StringUtils.isNotBlank((String) params.get("startTime"))){
            condition.setStartTime((String)params.get("startTime"));
        }
        if (StringUtils.isNotBlank((String) params.get("endTime"))){
            condition.setEndTime((String)params.get("endTime"));
        }
        if (params.get("companyId") != null){
            condition.setCompanyId((Long) params.get("companyId"));
        }
        page.setRecords(this.baseMapper.selectPageByCondition(page,condition));
        return new PageUtils(page);
    }
}
