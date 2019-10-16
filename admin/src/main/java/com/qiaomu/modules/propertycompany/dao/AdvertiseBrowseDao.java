package com.qiaomu.modules.propertycompany.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.propertycompany.entity.AdvertiseBrowse;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-10-15 15:47
 */
public interface AdvertiseBrowseDao extends BaseMapper<AdvertiseBrowse> {

    void insertOrUpdate(AdvertiseBrowse browse);

    List<AdvertiseBrowse> getStatistics(AdvertiseBrowse advertise);

    List<AdvertiseBrowse> getStatisticsDetail(AdvertiseBrowse advertise);
}
