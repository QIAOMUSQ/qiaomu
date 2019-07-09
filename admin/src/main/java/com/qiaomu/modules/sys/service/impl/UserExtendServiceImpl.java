package com.qiaomu.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.*;
import com.qiaomu.modules.sys.dao.SysUserDao;
import com.qiaomu.modules.sys.dao.UserExtendDao;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.sys.entity.UserExtend;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import com.qiaomu.modules.sys.service.UserExtendService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 李品先
 * @description:
 * @Date 2019-03-29 17:08
 */
@Service
public class UserExtendServiceImpl extends ServiceImpl<UserExtendDao, UserExtend> implements UserExtendService {

    @Autowired
    private YwCommunityService communityService;

    @Autowired
    private SysUserDao userDao;

    @Autowired
    private SysUserService sysUserService;


    @Override
    public List<UserExtend> getUserExtend(String userPhone) {
        return this.baseMapper.getUserExtend(userPhone);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String userPhone = (String) params.get("userPhone");
        String communityName = (String) params.get("communityName");
        Long companyId = (Long) params.get("companyId");
        String propertyCompanyRoleType = (String) params.get("companyRoleType");
        List<Long> communityIds = communityService.getCommunityIdList(communityName, companyId);
        Page<UserExtend> page = this.selectPage(
                new Query<UserExtend>(params).getPage(),
                new EntityWrapper<UserExtend>()
                        .eq(StringUtils.isNotBlank(userPhone), "USER_PHONE", userPhone)
                        .in(communityIds.size() > 0, "COMMUNITY_ID", communityIds)
                       // .or(companyId != null && companyId!=-1, "COMPANY_ID", companyId)
                        .ne(StringUtils.isNotBlank(propertyCompanyRoleType), "PROPERTY_COMPANY_ROLE_TYPE", propertyCompanyRoleType)//过滤物业管理员
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );

        for (UserExtend userExtend : page.getRecords()) {
            YwCommunity community = communityService.queryById(userExtend.getCommunityId());
            userExtend.setCommunityName(community == null ? "" : community.getName());
            userExtend.setRealName(AESUtil.decrypt(userExtend.getRealName()));
            userExtend.setAddress(AESUtil.decrypt(userExtend.getAddress()));
            userExtend.setUserPhone(sysUserService.queryById(userExtend.getUserId()).getUsername());
        }

        return new PageUtils(page);
    }

    @Override
    public UserExtend getUserExtendInfo(Long id) {
        UserExtend  user = this.selectById(id);
        YwCommunity community = communityService.queryById(user.getCommunityId());
        user.setCommunityName(community == null ? "" : community.getName());
        user.setRealName(AESUtil.decrypt(user.getRealName()));
        user.setAddress(AESUtil.decrypt(user.getAddress()));
        user.setUserPhone(sysUserService.queryById(user.getUserId()).getUsername());
        return user;
    }


    @Override
    public void delect(Long[] userIds) {
        this.deleteBatchIds(Arrays.asList(userIds));

    }

    @Override
    public String getUserByPhone(String phone) {
        String name = "";
        String[] phones = phone.split("_");
        for (String i : phones) {
            //name = name + getUserExtend(i).getRealName() + "、";
        }
        name = name.substring(0, name.length() - 1);
        return name;
    }

    /**
     *
     * @param userPhone
     * @param newPhone 新手机号码
     * @param nickName  昵称
     * @param sex   性别
     * @param imgId 图片id
     * @param communityId 社区id
     * @return
     */
    @Override
    @Transactional
    public String setPersonalCenter(String userPhone,String newPhone, String nickName, String sex, Long imgId,Long communityId) {

        try {
            SysUserEntity user = sysUserService.isExist(userPhone);
            //设置昵称
            if(StringUtils.isNotBlank(nickName)){
                user.setNickName(nickName);
            }
            //设置性别
            if(StringUtils.isNotBlank(sex)){
                user.setSex(sex);
            }
            //设置头像
            if(imgId != null){
                user.setHandImgId(imgId);
            }
            if(StringUtils.isNotBlank(newPhone)){
                user.setUsername(newPhone);
            }
            sysUserService.updateById(user);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }


    /**
     *
     * @param info      通过信息
     * @param type      0:待审核 1：通过 2：不通过 3：禁用
     * @param companyRoleType  物业角色
     * @param id 用户手机号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCheckInfo(String info, String type, String companyRoleType, Long id,Long userId) {
        UserExtend userExtend = this.baseMapper.selectById(id);
        userExtend.setCompanyRoleType(companyRoleType);
        userExtend.setInfo(info);
        userExtend.setCheck(type);
        userExtend.setCheckTime(new Date());
        userExtend.setCheckUserId(userId);
        updateById(userExtend);
    }
}
