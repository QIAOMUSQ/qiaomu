package com.qiaomu.modules.workflow.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.modules.workflow.entity.CarportEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-07-27 0:14
 */
public interface CarportService extends IService<CarportEntity> {

    List<CarportEntity> selectAll(CarportEntity carport);

    Boolean save(CarportEntity carport, HttpServletRequest request);

    CarportEntity getCarportById(Long id);

    List<CarportEntity> selectByCommunityId(Long communityId);

    void deleteByCommunityId(Long id);


    boolean deleteCarportById(Long id);

    void updateCarport(CarportEntity carportEntity, HttpServletRequest request);
}
