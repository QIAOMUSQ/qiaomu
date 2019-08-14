package com.qiaomu.modules.propertycompany.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.propertycompany.dao.AdvertiseDao;
import com.qiaomu.modules.propertycompany.dao.CommunityAdvertiseDao;
import com.qiaomu.modules.propertycompany.entity.Advertise;
import com.qiaomu.modules.propertycompany.entity.CommunityAdvertise;
import com.qiaomu.modules.propertycompany.entity.Merchant;
import com.qiaomu.modules.propertycompany.service.AdvertiseService;
import com.qiaomu.modules.sys.service.SysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-10 12:21
*/
@Service
public class AdvertiseServiceImpl extends ServiceImpl<AdvertiseDao,Advertise> implements AdvertiseService {

    @Autowired
    private SysFileService sysFileService;

    @Resource
    private CommunityAdvertiseDao communityAdvertiseDao;

    @Override
    public PageUtils pageList(Map<String, Object> params,Advertise advertise) {
        Page<Advertise> page = new Query(params).getPage();// 当前页，总条
        page.setRecords(this.baseMapper.selectPageAll(page,advertise));
        return new PageUtils(page);
    }


    @Override
    @Transactional
    public boolean save(Advertise advertise, HttpServletRequest request) {
        try {
            if (advertise.getId()== null){
                advertise.setCreateTime(new Date());
                this.baseMapper.insert(advertise);
                String[] ids = advertise.getCommunityIds().split(",");
                for (String id:ids){
                    CommunityAdvertise communityAdvertise = new CommunityAdvertise();
                    communityAdvertise.setCommunityId(Long.valueOf(id));
                    communityAdvertise.setAdvertiseId(advertise.getId());
                    communityAdvertiseDao.insert(communityAdvertise);
                }
            }else {
                Advertise old =  this.baseMapper.selectById(advertise.getId());
                String[] oldIds = old.getCommunityIds().split(",");
                String[] newIds = advertise.getCommunityIds().split(",");
                List<String> insetIds = new ArrayList<>();
                String deleteIds = "";

                for (String oldId : oldIds){
                    if(!advertise.getCommunityIds().contains(oldId)){
                        deleteIds += oldId+",";
                    }
                }
                for (String newId : newIds){
                    if(!old.getCommunityIds().contains(newId)){
                        insetIds.add(newId);
                    }
                }
                if(deleteIds.length()>0){
                    deleteIds = deleteIds.substring(0,deleteIds.length()-1);
                }
                communityAdvertiseDao.deleteByAdvertiseId(deleteIds);
                for (String id : insetIds){
                    CommunityAdvertise communityAdvertise = new CommunityAdvertise();
                    communityAdvertise.setCommunityId(Long.valueOf(id));
                    communityAdvertise.setAdvertiseId(advertise.getId());
                    communityAdvertiseDao.insert(communityAdvertise);
                }
                this.baseMapper.updateById(advertise);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Advertise> getAdvertiseByCommunity(Long communityId) {

        return this.baseMapper.getAdvertiseByCommunity(communityId);
    }
}
