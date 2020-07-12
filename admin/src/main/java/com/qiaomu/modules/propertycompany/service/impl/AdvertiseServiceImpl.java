package com.qiaomu.modules.propertycompany.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.propertycompany.dao.AdvertiseBrowseDao;
import com.qiaomu.modules.propertycompany.dao.AdvertiseDao;
import com.qiaomu.modules.propertycompany.dao.CommunityAdvertiseDao;
import com.qiaomu.modules.propertycompany.entity.Advertise;
import com.qiaomu.modules.propertycompany.entity.AdvertiseBrowse;
import com.qiaomu.modules.propertycompany.entity.CommunityAdvertise;
import com.qiaomu.modules.propertycompany.entity.Merchant;
import com.qiaomu.modules.propertycompany.service.AdvertiseService;
import com.qiaomu.modules.sys.dao.YwCommunityDao;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.sys.service.SysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Resource
    private YwCommunityDao communityDao;

    @Resource
    private AdvertiseBrowseDao advertiseBrowseDao;

    @Override
    public PageUtils pageList(Map<String, Object> params,Advertise advertise) {
        Page<Advertise> page = new Query(params).getPage();// 当前页，总条
        page.setRecords(this.baseMapper.selectPageAll(page,advertise));
        for (Advertise a :page.getRecords()){
            List<YwCommunity> communities= communityDao.getCommunityByAdvertise(a.getId());
            if(communities.size()>0){
                a.setCommunityName(communities.stream().map(p -> p.getName()).collect(Collectors.toList()).toString());
            }
        }
        return new PageUtils(page);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Advertise advertise, HttpServletRequest request) {
        if (advertise.getId()== null){
            advertise.setCreateTime(new Date());
            advertise.setImgs(advertise.getImgs().replace("\\",""));
            this.baseMapper.insert(advertise);
            String[] ids = advertise.getCommunityIds().split(",");
            //插入社区广告关联表
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
    }

    @Override
    public List<Advertise> getAdvertiseByCommunity(Long communityId) {
        return this.baseMapper.getAdvertiseByCommunity(communityId);
    }

    @Override
    public List<AdvertiseBrowse> getStatistics(AdvertiseBrowse advertise) {
        return advertiseBrowseDao.getStatistics(advertise);
    }

    @Override
    public List<AdvertiseBrowse> getStatisticsDetail(AdvertiseBrowse advertise) {
        return advertiseBrowseDao.getStatisticsDetail(advertise);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Serializable id) {
        Advertise advertise = baseMapper.selectById(id);
        if (advertise.getMainImg()!=null){
            sysFileService.deleteFileByHttpUrl(advertise.getMainImg());
        }
        //sysFileService.deleteByMap(advertise.get)
        return super.deleteById(id);
    }
}