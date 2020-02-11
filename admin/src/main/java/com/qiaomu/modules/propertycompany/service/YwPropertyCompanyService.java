package com.qiaomu.modules.propertycompany.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.propertycompany.entity.YwPropertyCompany;

import java.util.Map;

/**
 * @author 李品先
 * @description: 物业公司
 * @Date 2019-04-21 16:19
 */
public interface YwPropertyCompanyService extends IService<YwPropertyCompany> {
    PageUtils queryPage(Map<String, Object> paramMap);

    void save(YwPropertyCompany paramYwPropertyCompany);

    void update(YwPropertyCompany paramYwPropertyCompany);


    YwPropertyCompany findCompanyByUserId(Long userId);
}