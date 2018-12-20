package io.renren.modules.android.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.modules.android.service.AppUserService;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysRoleEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysRoleService;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.LongFunction;

/**
 * @author 李品先
 * @description:
 * @Date 2018-12-02 21:51
 */
@Service
public class AppUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements AppUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public void save(String phone, String password) {
        SysUserEntity user =new SysUserEntity();
        user.setUsername(phone);
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPassword(ShiroUtils.sha256(password, user.getSalt()));
        //user.setDeptId(Long.valueOf(communityId));
        user.setStatus(1);
        user.setCreateTime(new Date());
        //保存用户角色(普通用户)
        /*EntityWrapper<SysRoleEntity> role = new EntityWrapper<SysRoleEntity>();
        role.eq("role_name", "用户");
        role.eq("dept_id",communityId);
        role.eq("dept_role","1");
        SysRoleEntity roleEntity = sysRoleService.selectOne(role);
        List<Long> roleList = new ArrayList<>();
        roleList.add(roleEntity.getRoleId());
        user.setRoleIdList(roleList);*/
        this.insert(user);
        //sysUserRoleService.saveOrUpdate(user.getUserId(),user.getRoleIdList());
    }

}
