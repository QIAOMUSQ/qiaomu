package com.qiaomu.modules.propertycompany.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.propertycompany.entity.LoginStatistics;

import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-24 16:27
 */
public interface LoginStatisticsService extends IService<LoginStatistics> {
    List<LoginStatistics> getStatisticsDate(LoginStatistics condition);

    List<LoginStatistics> getStatisticsloginByDate(LoginStatistics condition);

    PageUtils pageList(Map<String, Object> params);
}
