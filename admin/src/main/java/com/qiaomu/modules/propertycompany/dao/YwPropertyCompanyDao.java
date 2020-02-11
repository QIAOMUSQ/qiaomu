package com.qiaomu.modules.propertycompany.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.propertycompany.entity.YwPropertyCompany;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 16:17
 */
public interface YwPropertyCompanyDao extends BaseMapper<YwPropertyCompany> {

    YwPropertyCompany findCompanyByUserId(Long userId);
}
