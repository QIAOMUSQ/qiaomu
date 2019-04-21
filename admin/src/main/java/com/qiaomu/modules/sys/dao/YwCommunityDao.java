package com.qiaomu.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.sys.entity.YwCommunity;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-19 22:56
 */
public interface YwCommunityDao extends BaseMapper<YwCommunity> {
    YwCommunity queryById(Long var1);
}
