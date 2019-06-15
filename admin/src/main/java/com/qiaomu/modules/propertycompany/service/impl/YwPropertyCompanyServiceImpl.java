package com.qiaomu.modules.propertycompany.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.propertycompany.dao.YwPropertyCompanyDao;
import com.qiaomu.modules.propertycompany.entity.YwPropertyCompany;
import com.qiaomu.modules.propertycompany.service.YwPropertyCompanyService;
import com.qiaomu.modules.sys.entity.UserExtend;
import com.qiaomu.modules.sys.service.UserExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public PageUtils queryPage(Map<String, Object> params) {
        Page<YwPropertyCompany> page = selectPage(new Query(params)
                .getPage(), new EntityWrapper()
                .addFilterIfNeed(params
                                .get("sql_filter") != null,
                        (String) params.get("sql_filter"), new Object[0]));

        return new PageUtils(page);
    }

    public void save(YwPropertyCompany propertyCompany) {
        ((YwPropertyCompanyDao) this.baseMapper).insert(propertyCompany);
        updateUserExtendInfo(propertyCompany);
    }

    public void update(YwPropertyCompany propertyCompany) {
        ((YwPropertyCompanyDao) this.baseMapper).updateById(propertyCompany);
        updateUserExtendInfo(propertyCompany);
    }

    private void updateUserExtendInfo(YwPropertyCompany propertyCompany) {
        UserExtend userExtend = this.userExtendService.getUserExtend(propertyCompany.getAdminPhone());
        if (userExtend != null) {
            userExtend.setCompanyId(propertyCompany.getId());
            this.userExtendService.updateById(userExtend);
        } else {
            UserExtend userExtends = new UserExtend();
            userExtends.setUserPhone(propertyCompany.getAdminPhone());
            userExtends.setCompanyId(propertyCompany.getId());
            this.userExtendService.insert(userExtends);
        }
    }
}
