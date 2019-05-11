package com.qiaomu.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.Constant;
import com.qiaomu.common.utils.DicRoleDeptCode;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.sys.dao.SysUserDao;
import com.qiaomu.modules.sys.dao.YwUserCheckInfoDao;
import com.qiaomu.modules.sys.dao.YwUserExtendDao;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.sys.entity.YwUserCheckInfo;
import com.qiaomu.modules.sys.entity.YwUserExtend;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.sys.service.YwCommunityService;
import com.qiaomu.modules.sys.service.YwUserExtendService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-03-29 17:08
 */
@Service
public class YwUserExtendServiceImpl extends ServiceImpl<YwUserExtendDao, YwUserExtend> implements YwUserExtendService {

    @Autowired
    private YwCommunityService communityService;

    @Autowired
    private SysUserDao userDao;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private YwUserCheckInfoDao checkInfoDao;

    @Override
    public YwUserExtend getUserExtend(String userPhone) {
        return this.baseMapper.getUserExtend(userPhone);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String userPhone = (String) params.get("userPhone");
        String communityName = (String) params.get("communityName");
        Long companyId = (Long) params.get("companyId");
        String propertyCompanyRoleType = (String) params.get("companyRoleType");
        List<Long> communityIds = communityService.getCommunityIdList(communityName, companyId);
        Page<YwUserExtend> page = this.selectPage(
                new Query<YwUserExtend>(params).getPage(),
                new EntityWrapper<YwUserExtend>()
                        .eq(StringUtils.isNotBlank(userPhone), "USER_PHONE", userPhone)
                        .in(communityIds.size() > 0, "COMMUNITY_ID", communityIds)
                        .eq(companyId != -1l, "COMPANY_ID", companyId)
                        .ne(StringUtils.isNotBlank(propertyCompanyRoleType), "PROPERTY_COMPANY_ROLE_TYPE", propertyCompanyRoleType)//过滤物业管理员
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );

        for (YwUserExtend userExtend : page.getRecords()) {
            YwCommunity community = communityService.queryById(userExtend.getCommunityId());
            userExtend.setCommunityName(community == null ? "" : community.getName());
        }

        return new PageUtils(page);
    }

    @Override
    public YwUserExtend getUserExtendInfo(Long id) {
        YwUserExtend userExtend = this.selectById(id);
        YwUserCheckInfo checkInfo = checkInfoDao.selectOneByPhone(userExtend.getUserPhone());
        if (checkInfo != null) {
            userExtend.setInfo(checkInfo.getInfo());
        }
        return userExtend;
    }


    /**
     * 用户审核信息
     *
     * @param userPhone 用户手机号
     * @param info      通过信息
     * @param type      1：通过 2：不通过 3:禁用
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCheckInfo(String userPhone, String info, String type, String roleType) {

        if (!type.equals("3")) {//1通过
            SysUserEntity user = userDao.getUserByUserName(userPhone);

            String[] role_dept = DicRoleDeptCode.role_dept_map.get(roleType).split("_");
            List<Long> roleList = new ArrayList<Long>();
            roleList.add(Long.valueOf(role_dept[0]));

            user.setRoleIdList(roleList);//设置角色类型
            user.setDeptId(Long.valueOf(role_dept[1]));
            user.setPropertyCompanyRoleType(roleType);
            sysUserService.update(user);
        }
        YwUserExtend userExtend = this.getUserExtend(userPhone);
        userExtend.setCheck(type);//更新审核标志
        this.updateById(userExtend);

    }

    @Override
    public void delect(Long[] userIds) {
        for (Long id : userIds) {
            YwUserExtend userExtend = this.selectById(id);
            SysUserEntity userEntity = userDao.getUserByUserName(userExtend.getUserPhone());
            userDao.deleteById(userEntity.getDeptId());
        }
        this.deleteBatchIds(Arrays.asList(userIds));

    }
}
