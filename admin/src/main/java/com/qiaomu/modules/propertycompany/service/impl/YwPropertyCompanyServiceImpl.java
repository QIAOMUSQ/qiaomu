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
import com.qiaomu.modules.sys.service.SysFileService;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.sys.service.UserExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
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

    @Autowired
    private SysFileService sysFileService;

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

    @Transactional(rollbackFor = Exception.class)
    public void save(YwPropertyCompany propertyCompany) {
        propertyCompany.setCreateTime(new Date());
        this.baseMapper.insert(propertyCompany);
        updateUserInfo(propertyCompany);
    }

    @Transactional(rollbackFor = Exception.class)
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
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        //删除图片
        for(Serializable id :idList){
            YwPropertyCompany company = baseMapper.selectById(id);
            String[] imgIds = company.getCompanyImg().split("_");
            for(String imgId: imgIds){
                sysFileService.deleteById(Long.valueOf(imgId));
            }
        }
        return super.deleteBatchIds(idList);
    }
}
