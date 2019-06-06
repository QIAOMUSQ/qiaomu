package com.qiaomu.modules.app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.modules.sys.entity.SysUserEntity;

/**
 * @author 李品先
 * @description:
 * @Date 2018-12-02 21:51
 */
public interface AppUserService extends IService<SysUserEntity> {

    /**
     * app 保存用户
     *
     * @param phone
     * @param password
     */
    void save(String phone, String password);

}
