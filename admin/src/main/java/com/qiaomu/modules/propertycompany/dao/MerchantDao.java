package com.qiaomu.modules.propertycompany.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.qiaomu.modules.propertycompany.entity.Merchant;
import com.qiaomu.modules.workflow.entity.YwWorkflowInfo;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-10 12:05
 */
public interface MerchantDao extends BaseMapper<Merchant> {

    List<Merchant> selectPageAll(Pagination page, Merchant info);
}
