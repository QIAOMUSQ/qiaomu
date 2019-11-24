package com.qiaomu.modules.app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.app.entity.AppUpdateEntity;
import com.qiaomu.modules.workflow.entity.InvitationEntity;


/**
     * Created by wenglei on 2018/11/14.
     */
    public interface AppUpdateDao extends BaseMapper<InvitationEntity> {


        void addAppUpdateInfo(AppUpdateEntity appUpdateEntity);

        AppUpdateEntity getMaxVersion(String clientType);

}
