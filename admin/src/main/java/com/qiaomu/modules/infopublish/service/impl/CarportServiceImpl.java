package com.qiaomu.modules.infopublish.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.modules.infopublish.dao.CarportDao;
import com.qiaomu.modules.infopublish.entity.CarportEntity;
import com.qiaomu.modules.infopublish.service.CarportService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-07-27 0:14
 */
@Service
public class CarportServiceImpl extends ServiceImpl<CarportDao,CarportEntity> implements CarportService {
    @Override
    public List<CarportEntity> selectAll(CarportEntity carport) {
        return this.baseMapper.selectAll(carport);
    }
}
