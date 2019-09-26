package com.qiaomu.modules.app.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.app.dao.CommunityCheckDao;
import com.qiaomu.modules.app.entity.CommunityCheckEntity;
import com.qiaomu.modules.app.service.CommunityCheckService;
import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import com.qiaomu.modules.propertycompany.service.YwPropertyCompanyService;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.sys.service.ProvinceCityDateService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author 李品先
 * @description: 社区审核
 * @Date 2019-06-04 19:47
 */
@Service
public class CommunityCheckServiceImpl extends ServiceImpl<CommunityCheckDao,CommunityCheckEntity> implements CommunityCheckService {


    @Autowired
    private ProvinceCityDateService provinceCityDateService;

    @Autowired
    private YwCommunityService communityService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        CommunityCheckEntity condition = new CommunityCheckEntity();
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        String isCheck = (String) params.get("isCheck");
        if(StringUtils.isNotBlank(startTime)){
            condition.setStartTime(startTime);
        }
        if(StringUtils.isNotBlank(endTime)){
            condition.setEndTime(endTime);
        }
        if (StringUtils.isNotBlank(isCheck)){
            condition.setIsCheck(isCheck);
        }
        Page<CommunityCheckEntity> page = new Query(params).getPage();// 当前页，总条
        page.setRecords(baseMapper.selectPageByCondition(condition));

        for (CommunityCheckEntity community : page.getRecords()){
            if(community.getCommunityId() != null){
                YwCommunity community1 = communityService.queryById(community.getCommunityId());
                if(community1 !=null ){
                    community.setCompanyName(community1.getCompanyName());
                }else {
                    community.setCommunityId(null);
                    baseMapper.updateAllColumnById(community);
                }
            }
        }
        return new PageUtils(page);
    }

    @Override
    public CommunityCheckEntity selectById(Serializable id) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        CommunityCheckEntity  community = super.selectById(id);
        community.setCityName(provinceCityDateService.getProCityByCityCode(community.getCityCode()).getCityName());
        community.setTime(sdf.format(community.getCreateTime()));
        return community;
    }


    @Override
    @Transactional(rollbackFor=Exception.class)
    public String save(CommunityCheckEntity community) {
        try {

            CommunityCheckEntity entity = this.selectById(community.getId());
            entity.setIsCheck(community.getIsCheck());
            entity.setRemark(community.getRemark());
            entity.setCheckDate(new Date());
            if(community.getIsCheck().equals("1")){
                YwCommunity community1 = new YwCommunity();
                community1.setName(community.getCommunityName());
                community1.setCityCode(community.getCityCode());
                community1.setAddress(community.getAddress());
                community1.setCreateTime(new Date());
                communityService.save(community1);
                entity.setCommunityId(community1.getId());
            }
            this.updateById(entity);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public String changeCompany(Long checkCommunityId, Long companyId) {
        try {
            CommunityCheckEntity communityCheckEntity = this.selectById(checkCommunityId);
            if(!communityCheckEntity.getIsCheck().equals("1")){
                return "通过状态下才能设置";
            }
            System.out.println("checkCommunityId = [" + checkCommunityId + "], companyId = [" + companyId + "]");
            YwCommunity community = communityService.queryById(communityCheckEntity.getCommunityId());
            community.setCompanyId(companyId);
            communityService.updateById(community);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
           throw e;
        }

    }
}
