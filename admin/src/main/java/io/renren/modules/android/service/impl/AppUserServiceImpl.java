package io.renren.modules.android.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.modules.android.service.AppUserService;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 李品先
 * @description:
 * @Date 2018-12-02 21:51
 */
@Service
public class AppUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements AppUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public void save(String phone, String password, Integer communityId) {
        SysUserEntity user =new SysUserEntity();
        user.setUsername(phone);
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPassword(ShiroUtils.sha256(password, user.getSalt()));
        user.setCommunityId(communityId);
        user.setStatus(1);
        this.insert(user);

    }
}
