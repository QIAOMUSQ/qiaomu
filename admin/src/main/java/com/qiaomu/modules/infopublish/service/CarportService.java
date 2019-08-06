package com.qiaomu.modules.infopublish.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.modules.infopublish.entity.CarportEntity;

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
}
