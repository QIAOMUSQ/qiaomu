package com.qiaomu.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.sys.entity.YwUserCheckInfo;

/**
 * @author 李品先
 * @description:
 * @Date 2019-03-31 21:53
 */
public interface YwUserCheckInfoDao extends BaseMapper<YwUserCheckInfo> {
    YwUserCheckInfo selectOneByPhone(String phone);
}
