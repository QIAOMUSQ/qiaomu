package com.qiaomu.modules.propertycompany.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.propertycompany.dao.YwPropertyCompanyDao;
import com.qiaomu.modules.propertycompany.entity.YwPropertyCompany;
import com.qiaomu.modules.propertycompany.service.YwPropertyCompanyService;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.UserExtend;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.sys.service.UserExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 16:20
 */
@Service
public class YwPropertyCompanyServiceImpl extends ServiceImpl<YwPropertyCompanyDao, YwPropertyCompany>
        implements YwPropertyCompanyService {

    @Autowired
    private UserExtendService userExtendService;

    @Autowired
    private SysUserService userService;

    public PageUtils queryPage(Map<String, Object> params) {
        Page<YwPropertyCompany> page = selectPage(new Query(params)
                .getPage(), new EntityWrapper()
                .addFilterIfNeed(params
                                .get("sql_filter") != null,
                        (String) params.get("sql_filter"), new Object[0]));

        for (YwPropertyCompany company : page.getRecords()){
            company.setAdministratorName(userService.selectById(company.getAdministratorId()).getRealName());
        }
        return new PageUtils(page);
    }

    @Transactional
    public void save(YwPropertyCompany propertyCompany) {
        propertyCompany.setCreateTime(new Date());
        this.baseMapper.insert(propertyCompany);
        updateUserInfo(propertyCompany);
    }

    @Transactional
    public void update(YwPropertyCompany propertyCompany) {
        this.baseMapper.updateById(propertyCompany);
        updateUserInfo(propertyCompany);
    }

    /**
     * 将管理员信息保存到人员信息表中
     * @param propertyCompany
     */
    private void updateUserInfo(YwPropertyCompany propertyCompany) {
        SysUserEntity user = userService.selectById(propertyCompany.getAdministratorId());
        user.setCompanyId(propertyCompany.getId());
        userService.updateById(user);
//        UserExtend userExtend = this.userExtendService.getUserExtend(propertyCompany.getAdminPhone());
//        if (userExtend != null) {
//            userExtend.setCompanyId(propertyCompany.getId());
//            this.userExtendService.updateById(userExtend);
//        } else {
//            UserExtend userExtends = new UserExtend();
//            userExtends.setUserPhone(propertyCompany.getAdminPhone());
//            userExtends.setCompanyId(propertyCompany.getId());
//            this.userExtendService.insert(userExtends);
//        }
    }
}
