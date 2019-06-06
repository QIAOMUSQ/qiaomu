package com.qiaomu.modules.app.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.modules.sys.service.SysUserRoleService;
import com.qiaomu.modules.app.service.AppUserService;
import com.qiaomu.modules.sys.dao.SysUserDao;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.service.SysRoleService;
import com.qiaomu.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        SysUserEntity user = new SysUserEntity();
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
