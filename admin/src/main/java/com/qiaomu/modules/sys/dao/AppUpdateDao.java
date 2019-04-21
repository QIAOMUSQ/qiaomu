package com.qiaomu.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.sys.entity.AppUpdateEntity;


/**
 * Created by wenglei on 2018/11/14.
 */
public interface AppUpdateDao extends BaseMapper<AppUpdateEntity> {


    void addAppUpdateInfo(AppUpdateEntity appUpdateEntity);

    AppUpdateEntity getMaxVersion();

}
