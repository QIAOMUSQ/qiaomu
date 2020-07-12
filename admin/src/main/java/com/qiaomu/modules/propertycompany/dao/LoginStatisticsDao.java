package com.qiaomu.modules.propertycompany.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qiaomu.modules.propertycompany.entity.LoginStatistics;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-24 16:25
 */
public interface LoginStatisticsDao extends BaseMapper<LoginStatistics> {


    @Override
    Integer insert(LoginStatistics loginStatistics);

    List<LoginStatistics> getStatisticsDate(LoginStatistics condition);

    List<LoginStatistics> getStatisticsLoginByDate(LoginStatistics condition);

    List<LoginStatistics> selectPageByCondition(Page<LoginStatistics> page, LoginStatistics condition);

    void deleteByCommunityId(Long communityId);

    int statisticsLoginByCommunityId(String communityId);
    int loginTimesTodayByCommunityId(String communityId);
}