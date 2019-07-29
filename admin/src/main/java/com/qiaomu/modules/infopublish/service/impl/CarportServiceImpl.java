package com.qiaomu.modules.infopublish.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.modules.infopublish.dao.CarportDao;
import com.qiaomu.modules.infopublish.entity.CarportEntity;
import com.qiaomu.modules.infopublish.service.CarportService;
import com.qiaomu.modules.sys.service.SysFileService;
import com.qiaomu.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-07-27 0:14
 */
@Service
public class CarportServiceImpl extends ServiceImpl<CarportDao,CarportEntity> implements CarportService {

    @Autowired
    private SysFileService fileService;

    @Autowired
    private SysUserService userService;

    @Override
    public List<CarportEntity> selectAll(CarportEntity carport) {
        List<CarportEntity> carportList = this.baseMapper.selectAll(carport);
        for (CarportEntity entity : carportList){
            Long handImgId = userService.queryById(entity.getUserId()).getHandImgId();
            if(handImgId!=null){
                carport.setHandImg(fileService.selectById(handImgId).getPath());
            }
        }
        return carportList;
    }

    @Override
    @Transactional
    public Boolean save(CarportEntity carport, HttpServletRequest request) {
        try {
            carport.setImgPath(JSON.toJSONString(fileService.imageUrls(request)));
            carport.setCreateTime(new Date());
            baseMapper.insert(carport);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    @Transactional
    public CarportEntity getCarportById(Long id) {
        CarportEntity carport = this.selectById(id);
        if(carport.getBrowsePerson()==null || carport.getBrowsePerson()==-1l){
            carport.setBrowsePerson(1l);
        }else {
            carport.setBrowsePerson(carport.getBrowsePerson()+1);
        }
        baseMapper.updateById(carport);
        return carport;
    }
}
