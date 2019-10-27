package com.qiaomu.modules.workflow.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.workflow.entity.UserRepairs;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-10-25 23:01
 */
public interface UserRepairsDao extends BaseMapper<UserRepairs> {

    List<UserRepairs> selectByRepairsId(Long repairsId);

}
