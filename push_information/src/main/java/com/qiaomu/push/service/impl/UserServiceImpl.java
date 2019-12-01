package com.qiaomu.push.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.push.dao.UserDao;
import com.qiaomu.push.entity.User;
import com.qiaomu.push.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-11-07 11:19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Override
    public List<User> findUserByCommunityId(Long communityId) {
        return baseMapper.findUserByCommunityId(communityId);
    }
}
