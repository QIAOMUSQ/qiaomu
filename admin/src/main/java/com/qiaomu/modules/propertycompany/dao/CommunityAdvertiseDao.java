package com.qiaomu.modules.propertycompany.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.propertycompany.entity.CommunityAdvertise;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-10 16:10
 */
public interface CommunityAdvertiseDao extends BaseMapper<CommunityAdvertise>{
    List<CommunityAdvertise> selectListByCondition(CommunityAdvertise advertise1);

    void deleteByAdvertiseId(String deleteIds);

    void deleteByCommunityId(Long communityId);
}
