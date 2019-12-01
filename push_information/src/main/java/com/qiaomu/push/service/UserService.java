package com.qiaomu.push.service;

import com.qiaomu.push.entity.User;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-11-07 11:19
 */
public interface UserService {
    List<User> findUserByCommunityId(Long communityId);

}
