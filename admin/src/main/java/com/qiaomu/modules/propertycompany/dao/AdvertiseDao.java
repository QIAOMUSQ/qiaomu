package com.qiaomu.modules.propertycompany.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qiaomu.modules.propertycompany.entity.Advertise;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-10 12:06
 */
public interface AdvertiseDao extends BaseMapper<Advertise> {
    List<Advertise> selectPageAll(Page<Advertise> page, Advertise advertise);

    List<Advertise> getAdvertiseByCommunity(Long communityId);
}
