package com.qiaomu.modules.propertycompany.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.AESUtil;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.sys.dao.SysUserDao;
import com.qiaomu.modules.sys.dao.SysUserRoleDao;
import com.qiaomu.modules.sys.dao.YwCommunityDao;
import com.qiaomu.modules.sys.dao.UserExtendDao;
import com.qiaomu.modules.sys.entity.*;
import com.qiaomu.modules.sys.service.ProvinceCityDateService;
import com.qiaomu.modules.sys.service.SysFileService;
import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
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

    @Resource
    private SysUserRoleDao userRoleDao;

    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        Long companyId =null;
        String cityName = (String) params.get("cityName");
        YwCommunity condition = new YwCommunity();

       if (StringUtils.isNotBlank(cityName)){
           condition.setCityName(cityName);
       }
        if(params.get("companyId") != null ){
            companyId = (Long)params.get("companyId");
            condition.setCompanyId(companyId);
        }
        if (StringUtils.isNotBlank(name))condition.setName(name);
        Page<YwCommunity> page = new Query(params).getPage();// 当前页，总条
        page.setRecords(this.baseMapper.selectPageByCondition(page,condition));
        for (YwCommunity community:page.getRecords()){
            UserExtend userExtend = userExtendDao.selectById(community.getAdminId());
            if (userExtend!=null){
                community.setAdmin(AESUtil.decrypt(userExtend.getRealName()));
            }

        }
        return new PageUtils(page);
    }

    public YwCommunity queryById(Long communityId) {
        YwCommunity community =this.baseMapper.queryById(communityId);

        return community;
    }

    @Transactional
    public void save(YwCommunity community) {
       // Map params = new HashMap();
        //params.put("cityCode", community.getCityCode());

        //List cityDate = this.provinceCityDateService.getProvinceCityDate(params);
       // if (cityDate.size() == 1) community.setCityId(((ProvinceCityDateEntity) cityDate.get(0)).getId());
        ProvinceCityDateEntity provinceCity = provinceCityDateService.selectOne(new EntityWrapper<ProvinceCityDateEntity>().eq("CITY_CODE",community.getCityCode()));
        community.setCityId(provinceCity.getId());
        community.setCreateTime(new Date());
        if (community.getId() != null)
            updateById(community);
        else{
            community.setEnable("1");
            this.baseMapper.insert(community);
        }
    }

    public List<Long> getCommunityIdList(String communityName, Long companyId) {
        List<YwCommunity> communityList = new ArrayList<>();
        List<Long> communityId = new ArrayList();

        YwCommunity community = new YwCommunity();
        if(companyId !=null ){
            community.setCompanyId(companyId);
        }
        if (StringUtils.isNotBlank(communityName)) {
            community.setName(communityName);
        }
        communityList = this.baseMapper.findAllByCondition(community);
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
        YwCommunity community = this.baseMapper.queryById(communityId);

        SysUserEntity user = userDao.getUserByUserName(phone);

        UserExtend userExtend = new UserExtend();
        userExtend.setUserId(user.getUserId());
        userExtend.setCommunityId(communityId);
        List<UserExtend> userList = userExtendDao.selectCommunityList(userExtend);

        userExtend.setAddress(AESUtil.encrypt(address));
        userExtend.setRealName(AESUtil.encrypt(realName));
        if (community.getCompanyId() != null && community.getCompanyId() != -1l) {
            userExtend.setCompanyId(community.getCompanyId());
        }
        userExtend.setStatus(true);
        userExtend.setCheck("0");
        userExtend.setCompanyRoleType("4");
        userExtend.setCreateTime(new Date());
        if (userList.size() == 0) {
            userExtendDao.insert(userExtend);
            return "保存成功";
        }else {
            UserExtend userExtend1 = userList.get(0);
            try {
                if (phone.equals(user.getUsername())
                        && realName.equals(AESUtil.decrypt(userExtend1.getRealName()))
                        && address.equals(AESUtil.decrypt(userExtend1.getAddress()))) {
                    return "信息未变更，请修改后重新提交";
                }
            }catch (Exception e){
                return "信息条目不能存在空值";
            }
            userExtend.setId(userList.get(0).getId());
            userExtendDao.updateById(userExtend);
            return "修改成功，系统重新审核！";
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

            if(user2.getRealName()==null){
                user2.setRealName(AESUtil.decrypt(userDao.queryById(userId).getRealName()));
            }else {
                user2.setRealName(AESUtil.decrypt(user2.getRealName()));
            }
            user2.setAddress(AESUtil.decrypt(user2.getAddress()));
            //物业信息
            user2.setCommunityName(this.baseMapper.selectById(user2.getCommunityId()).getName());
            System.out.println("user2 = [" + JSON.toJSONString(user2) );
        }
        return user2;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setCommunityDisEnable(Long[] ids) {
        Map<String,Object> map = new HashMap<>();
        map.put("deleteTime", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        map.put("ids",Arrays.asList(ids));
        baseMapper.setCommunityDisEnable(map);
    }

    @Override
    public List<YwCommunity> getDeleteCommunity(YwCommunity community) {
        return baseMapper.getDeleteCommunity(community);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setCommunityAdministrator(String userId, String communityId) {
        YwCommunity community = baseMapper.selectById(communityId);
        if (community.getAdminId()!=null){
            //删除原来的管理员角色
            UserExtend  userExtend = userExtendDao.selectById(Long.valueOf(userId));
            EntityWrapper<SysUserRoleEntity> wrapper = new EntityWrapper();
            wrapper.eq("user_id",userExtend.getUserId());
            List<SysUserRoleEntity> userRoleList = userRoleDao.selectList(wrapper);
            userRoleList.forEach(c->{
                c.setRoleId(5l);
                userRoleDao.updateById(c);
            });
        }
        community.setAdminId(Long.valueOf(userId));
        baseMapper.updateById(community);
        //修改用户和角色
        UserExtend  userExtend = userExtendDao.selectById(Long.valueOf(userId));
        EntityWrapper<SysUserRoleEntity> wrapper = new EntityWrapper();
        wrapper.eq("user_id",userExtend.getUserId());
        List<SysUserRoleEntity> userRoleList =  userRoleDao.selectList(wrapper);
        userRoleList.forEach(c->{
            c.setRoleId(3l);
            userRoleDao.updateById(c);
        });
    }
}
