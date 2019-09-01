package com.qiaomu.modules.app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.app.entity.CommunityCheckEntity;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-06-04 19:43
 */
public interface CommunityCheckDao  extends BaseMapper<CommunityCheckEntity> {
    List<CommunityCheckEntity> selectPageByCondition(CommunityCheckEntity condition);
}
