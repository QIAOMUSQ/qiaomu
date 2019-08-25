package com.qiaomu.modules.propertycompany.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.modules.propertycompany.entity.LoginStatistics;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-24 16:27
 */
public interface LoginStatisticsService extends IService<LoginStatistics> {
    List<LoginStatistics> getStatisticsDate(LoginStatistics condition);

    List<LoginStatistics> getStatisticsloginByDate(LoginStatistics condition);
}
