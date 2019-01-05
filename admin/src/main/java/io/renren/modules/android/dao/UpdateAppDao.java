package io.renren.modules.android.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.renren.modules.android.entity.AppUpdateEntity;


/**
 * Created by wenglei on 2018/11/14.
 */
public interface UpdateAppDao extends BaseMapper<AppUpdateEntity> {


    void addAppUpdateInfo(AppUpdateEntity appUpdateEntity);

    AppUpdateEntity getMaxVersion();

}
