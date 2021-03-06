package io.renren.modules.android.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.modules.sys.entity.SysUserEntity;

/**
 * @author 李品先
 * @description:
 * @Date 2018-12-02 21:51
 */
public interface AppUserService extends IService<SysUserEntity> {

    /**
     * app 保存用户
     * @param phone
     * @param password
     * @param communityId
     */
    void save(String phone,String password);

}
