package com.qiaomu.modules.infopublish.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.Constant;
import com.qiaomu.modules.article.exception.CommentException;
import com.qiaomu.modules.infopublish.dao.CarportDao;
import com.qiaomu.modules.infopublish.entity.CarportEntity;
import com.qiaomu.modules.infopublish.service.CarportService;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.service.SysFileService;
import com.qiaomu.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public List<CarportEntity> selectAll(CarportEntity carport) {
        List<CarportEntity> carportList = this.baseMapper.selectAll(carport);
        for (CarportEntity entity : carportList){
            SysUserEntity sysUserEntity = userService.queryById(Long.valueOf(entity.getUserId()));
            Long handImgId = sysUserEntity.getHandImgId();
            if(handImgId!=null){
                entity.setHandImg(fileService.selectById(handImgId).getPath());
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
        try{
            if(carport.getBrowsePerson()==null || carport.getBrowsePerson()==-1l){
                carport.setBrowsePerson(1l);
                redisTemplate.boundHashOps("browse_person").put(Constant.REDIS_KEY_CARPORT+carport.getId(),"1");
                baseMapper.updateById(carport);
            }else {
                Long browsePerson =0L;
                if(redisTemplate.boundHashOps("browse_person").hasKey(Constant.REDIS_KEY_CARPORT+carport.getId())){
                    browsePerson = Long.valueOf((String) redisTemplate.boundHashOps("browse_person").get(Constant.REDIS_KEY_CARPORT+carport.getId()));
                }else {
                    browsePerson = carport.getBrowsePerson();
                }
                carport.setBrowsePerson(browsePerson+1);
                redisTemplate.boundHashOps("browse_person").put(Constant.REDIS_KEY_CARPORT+carport.getId(),String.valueOf(browsePerson+1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return carport;
    }

    @Override
    public List<CarportEntity> selectByCommunityId(Long communityId) {
        return baseMapper.selectByCommunityId(communityId);
    }

    @Override
    public void deleteByCommunityId(Long communityId) {
         baseMapper.deleteByCommunityId(communityId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCarportById(Long id) {
        CarportEntity carport =baseMapper.selectById(id);
        if (carport != null){
            Map<String,String> map = JSONObject.parseObject(carport.getImgPath(), Map.class);//jsonmap转map
            map.forEach((m,n) -> fileService.deleteFileByHttpUrl(n));
        }
        if (baseMapper.deleteById(id)>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCarport(CarportEntity carportEntity, HttpServletRequest request) {
        CarportEntity carport =baseMapper.selectById(carportEntity.getId());
        Map<String,String> map = JSONObject.parseObject(carport.getImgPath(), Map.class);
        map.forEach((m,n) -> fileService.deleteFileByHttpUrl(n));
        carportEntity.setImgPath(JSON.toJSONString(fileService.imageUrls(request)));
        baseMapper.updateById(carportEntity);
    }

}
