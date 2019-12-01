package com.qiaomu.push.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.push.entity.User;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-11-07 13:49
 */
public interface UserDao extends BaseMapper<User>{

    List<User> findUserByCommunityId(Long communityId);
}
