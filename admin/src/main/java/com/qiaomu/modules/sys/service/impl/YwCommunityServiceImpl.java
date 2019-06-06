package com.qiaomu.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.AESUtil;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.sys.dao.SysUserDao;
import com.qiaomu.modules.sys.dao.YwCommunityDao;
import com.qiaomu.modules.sys.dao.YwUserExtendDao;
import com.qiaomu.modules.sys.entity.ProvinceCityDateEntity;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.sys.entity.YwUserExtend;
import com.qiaomu.modules.sys.service.ProvinceCityDateService;
import com.qiaomu.modules.sys.service.YwCommunityService;
import com.qiaomu.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-19 22:51
 */

@Service
public class YwCommunityServiceImpl extends ServiceImpl<YwCommunityDao, YwCommunity> implements YwCommunityService {
    @Autowired
    private SysUserDao userDao;

    @Autowired
    private ProvinceCityDateService provinceCityDateService;

    @Autowired
    private YwUserExtendDao userExtendDao;

    @Autowired
    private YwCommunityService communityService;

    @Autowired
    private SysFileService fileService;

    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        Long companyId = (Long) params.get("companyId");
        String cityName = (String) params.get("cityName");
        ProvinceCityDateEntity provinceCity = this.provinceCityDateService.getProvCityByCityName(cityName);
        Page<YwCommunity> page = selectPage(new Query(params)
                .getPage(), new EntityWrapper()
                .like(StringUtils.isNotBlank(name),
                        "name", name)
                .eq(companyId != null, "COMPANY_ID", companyId)
                .eq(provinceCity != null, "CITY_ID",
                        Long.valueOf(provinceCity == null ? 0L : provinceCity
                                .getId().longValue()))
                .addFilterIfNeed(params
                                .get("sql_filter") != null,
                        (String) params.get("sql_filter"), new Object[0]));

        for (YwCommunity community : page.getRecords()) {
            community.setCityName(((ProvinceCityDateEntity) this.provinceCityDateService.selectById(community.getCityId())).getCityName());
        }

        return new PageUtils(page);
    }


    @Transactional
    public String addCommunityMember(String pathId, String phone, Long communityId, String realName, String address, String identityInfo, String sex) {
        YwCommunity community = this.communityService.queryById(communityId);

        YwUserExtend userExtend = this.userExtendDao.getUserExtend(phone);
        userExtend.setAddress(AESUtil.encrypt(address));
        userExtend.setImgId(Long.valueOf(pathId));
        userExtend.setRealName(AESUtil.encrypt(realName));
        userExtend.setUserIdentity(identityInfo);
        userExtend.setUserPhone(phone);
        userExtend.setCommunityId(communityId);
        userExtend.setCompanyId(community.getCompanyId());
        userExtend.setSex(sex);

        this.userExtendDao.updateById(userExtend);
        return "ok";
    }

    public YwCommunity queryById(Long communityId) {
        YwCommunity community = ((YwCommunityDao) this.baseMapper).queryById(communityId);

        return community;
    }

    public List<YwCommunity> findAll(YwCommunity community) {
        return ((YwCommunityDao) this.baseMapper).selectList(new EntityWrapper()
                .like(StringUtils.isNotBlank(community
                        .getName()), "name", community.getName())
                .eq(community
                        .getCompanyId().longValue() != -1L, "company_id", community.getCompanyId())
                .eq(community
                        .getCityId().longValue() != -1L, "city_id", community.getCityId()));
    }

    public void save(YwCommunity community) {
        Map params = new HashMap();
        params.put("cityCode", community.getCityCode());

        List cityDate = this.provinceCityDateService.getProvinceCityDate(params);
        if (cityDate.size() == 1) community.setCityId(((ProvinceCityDateEntity) cityDate.get(0)).getId());

        community.setCreatTime(new Date());
        if (community.getId() != null)
            updateById(community);
        else
            ((YwCommunityDao) this.baseMapper).insert(community);
    }

    public List<Long> getCommunityIdList(String communityName, Long companyId) {
        List<YwCommunity> communityList = new ArrayList<>();
        List<Long> communityId = new ArrayList();

        if (StringUtils.isNotBlank(communityName)) {
            YwCommunity community = new YwCommunity();
            community.setCompanyId(companyId);
            community.setName(communityName);
            communityList = this.communityService.findAll(community);
            for (YwCommunity communitys : communityList) {
                communityId.add(communitys.getId());
            }
        }
        return communityId;
    }

    @Override
    public List<YwCommunity> findAllByCondition(YwCommunity condition) {
        return baseMapper.findAllByCondition(condition);
    }
}
