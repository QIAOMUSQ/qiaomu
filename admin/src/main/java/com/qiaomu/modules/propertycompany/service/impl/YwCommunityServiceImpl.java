package com.qiaomu.modules.propertycompany.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.AESUtil;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.sys.dao.SysUserDao;
import com.qiaomu.modules.sys.dao.YwCommunityDao;
import com.qiaomu.modules.sys.dao.UserExtendDao;
import com.qiaomu.modules.sys.entity.ProvinceCityDateEntity;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.sys.entity.UserExtend;
import com.qiaomu.modules.sys.service.ProvinceCityDateService;
import com.qiaomu.modules.sys.service.SysFileService;
import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-19 22:51
 */

@Service
public class YwCommunityServiceImpl extends ServiceImpl<YwCommunityDao, YwCommunity> implements YwCommunityService {

    @Resource
    private SysUserDao userDao;

    @Autowired
    private ProvinceCityDateService provinceCityDateService;

    @Resource
    private UserExtendDao userExtendDao;

    @Autowired
    private YwCommunityService communityService;


    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        Long companyId =null;
        String cityName = (String) params.get("cityName");
        ProvinceCityDateEntity provinceCity = this.provinceCityDateService.getProvCityByCityName(cityName);
        if(params.get("companyId") != null ){
            companyId = (Long)params.get("companyId");
        }
        Page<YwCommunity> page = selectPage(new Query(params)
                .getPage(), new EntityWrapper()
                .like(StringUtils.isNotBlank(name), "name", name)
                .eq(companyId != null, "COMPANY_ID", companyId)
                .eq(provinceCity != null, "CITY_ID",
                        Long.valueOf(provinceCity == null ? 0L : provinceCity.getId().longValue()))
                .addFilterIfNeed(params
                                .get("sql_filter") != null,
                        (String) params.get("sql_filter"), new Object[0]));

        for (YwCommunity community : page.getRecords()) {
            community.setCityName(this.provinceCityDateService.selectById(community.getCityId()).getCityName());
        }

        return new PageUtils(page);
    }

    public YwCommunity queryById(Long communityId) {
        YwCommunity community =this.baseMapper.queryById(communityId);

        return community;
    }

    public List<YwCommunity> findAll(YwCommunity community) {
        return this.baseMapper.selectList(new EntityWrapper()
                .like(StringUtils.isNotBlank(community.getName()), "name", community.getName())
                .eq(community.getCompanyId() != null, "company_id", community.getCompanyId())
                .eq( community.getCityId() != null, "city_id", community.getCityId()));
    }

    public void save(YwCommunity community) {
        Map params = new HashMap();
        params.put("cityCode", community.getCityCode());

        List cityDate = this.provinceCityDateService.getProvinceCityDate(params);
        if (cityDate.size() == 1) community.setCityId(((ProvinceCityDateEntity) cityDate.get(0)).getId());

        community.setCreateTime(new Date());
        if (community.getId() != null)
            updateById(community);
        else
            this.baseMapper.insert(community);
    }

    public List<Long> getCommunityIdList(String communityName, Long companyId) {
        List<YwCommunity> communityList = new ArrayList<>();
        List<Long> communityId = new ArrayList();

        YwCommunity community = new YwCommunity();
        community.setCompanyId(companyId);
        if (StringUtils.isNotBlank(communityName)) {
            community.setName(communityName);
        }
        communityList = this.communityService.findAll(community);

        for (YwCommunity communitys : communityList) {
            communityId.add(communitys.getId());
        }
        return communityId;
    }

    @Override
    public List<YwCommunity> findAllByCondition(YwCommunity condition) {
        return baseMapper.findAllByCondition(condition);
    }

    @Override
    @Transactional
    public String addCommunityMember(String pathId, String phone, Long communityId, String realName, String address, String identityInfo, String sex) {
        YwCommunity community = this.communityService.queryById(communityId);

        SysUserEntity user = userDao.getUserByUserName(phone);

        UserExtend userExtend = new UserExtend();
        userExtend.setUserId(user.getUserId());
        userExtend.setCommunityId(communityId);
        List<UserExtend> userList = userExtendDao.selectCommunityList(userExtend);
        if(userList.size() ==0){
            userExtend.setAddress(AESUtil.encrypt(address));
            if(StringUtils.isNotBlank(pathId)){
            //    userExtend.setImgId(Long.valueOf(pathId));
            }
            userExtend.setRealName(AESUtil.encrypt(realName));
           // userExtend.setUserIdentity(identityInfo);

            if(community.getCompanyId() != null && community.getCompanyId()!=-1l){
                userExtend.setCompanyId(community.getCompanyId());
            }
            userExtend.setStatus(true);
            userExtend.setCheck("0");
            userExtend.setCompanyRoleType("5");
           // userExtend.setSex(sex);
            userExtend.setCreateTime(new Date());
            userExtendDao.insert(userExtend);
            return "保存成功";
        }else {
            return "重复提交信息";
        }


    }


    @Override
    public UserExtend getCommunityUserPermission(Long userId, Long communityId) {
        UserExtend user  = new UserExtend();
        user.setUserId(userId);
        user.setCommunityId(communityId);
        UserExtend user2 = userExtendDao.selectAll(user);
        System.out.println("userEntity = [" + JSON.toJSONString(user2));
        if(user2 != null){
            //用户信息
            SysUserEntity userEntity = userDao.queryById(userId);

            if(userEntity !=null && userEntity.getUsername() != null){
                user2.setUserPhone(userEntity.getUsername());
            }
            user2.setRealName(AESUtil.decrypt(user2.getRealName()));
            user2.setAddress(AESUtil.decrypt(user2.getAddress()));
            //物业信息
            user2.setCommunityName(communityService.selectById(user2.getCommunityId()).getName());
            System.out.println("user2 = [" + JSON.toJSONString(user2) );
        }
        return user2;
    }


}
