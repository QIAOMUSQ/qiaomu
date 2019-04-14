package io.renren.modules.android.service.impl;

import io.renren.modules.android.service.UserService;

import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author wonly
 * Create By 2019/1/1
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserDao sysUserDao;


    @Override
    public boolean checkUserName(String username) {
        SysUserEntity user = sysUserDao.findByUsername(username);
        if (user == null) return true;
        return false;
    }

    @Override
    public boolean checkUserEmail(String email) {
        SysUserEntity user = sysUserDao.findByEmail(email);
        if (user == null) return true;
        return false;
    }

    @Override
    public SysUserEntity findByEmail(String email) {
        return sysUserDao.findByEmail(email);
    }

    @Override
    public void createUser(String email, String username, String password) {
        SysUserEntity user = new SysUserEntity();
        user.setEmail(email);
        user.setUsername(username);
        user.setInitTime(new Date());
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        sysUserDao.save(user);
    }

    @Override
    public String LoginUser(SysUserEntity user) {
        String token = UUID.randomUUID().toString();
//        loginId.add(user.getId());//维护一个登录用户的set
        return token;
    }

    @Override
    public SysUserEntity getUserByToken(String token) {

        return null;
    }

    @Override
    public void LogoutUser(String token) {

    }

    @Override
    public void updateUser(String token, String username, String signature, Integer sex) {

    }

    @Override
    public void updataUserIcon(String token, String icon) {

    }


    @Override
    public void updateUserPassword(String token, String oldpsd, String newpsd) {

    }


}
