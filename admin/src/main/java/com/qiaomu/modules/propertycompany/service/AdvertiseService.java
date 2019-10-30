package com.qiaomu.modules.propertycompany.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.propertycompany.entity.Advertise;
import com.qiaomu.modules.propertycompany.entity.AdvertiseBrowse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-10 12:20
 */
public interface AdvertiseService extends IService<Advertise> {
    PageUtils pageList(Map<String, Object> params,Advertise advertise);

    boolean save(Advertise advertise, HttpServletRequest request);

    List<Advertise> getAdvertiseByCommunity(Long communityId);

    List<AdvertiseBrowse> getStatistics(AdvertiseBrowse advertise);

    List<AdvertiseBrowse>  getStatisticsDetail(AdvertiseBrowse advertise);
}
