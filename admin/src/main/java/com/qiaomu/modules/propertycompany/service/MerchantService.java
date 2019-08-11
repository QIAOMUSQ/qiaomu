package com.qiaomu.modules.propertycompany.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.propertycompany.entity.Merchant;

import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-10 12:42
 */
public interface MerchantService extends IService<Merchant> {
    PageUtils pageList(Map<String, Object> params);
}
