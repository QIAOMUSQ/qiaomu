package com.qiaomu.modules.workflow.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.workflow.entity.CarportEntity;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-07-27 0:12
 */

public interface CarportDao  extends BaseMapper<CarportEntity> {
    List<CarportEntity> selectAll(CarportEntity carport);

    List<CarportEntity> selectByCommunityId(Long communityId);

    void deleteByCommunityId(Long communityId);

}
