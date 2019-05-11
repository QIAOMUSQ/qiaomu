package com.qiaomu.modules.propertycompany.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.propertycompany.entity.YwPropertyCompany;

import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 16:19
 */
public interface YwPropertyCompanyService extends IService<YwPropertyCompany> {
    public abstract PageUtils queryPage(Map<String, Object> paramMap);

    public abstract void save(YwPropertyCompany paramYwPropertyCompany);

    public abstract void update(YwPropertyCompany paramYwPropertyCompany);
}