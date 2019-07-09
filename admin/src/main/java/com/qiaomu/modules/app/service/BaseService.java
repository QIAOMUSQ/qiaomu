package com.qiaomu.modules.app.service;

import com.qiaomu.modules.app.dao.AppUpdateDao;
import com.qiaomu.modules.app.entity.AppUpdateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 13:48
 */
@Service
public class BaseService {

    @Autowired
    private AppUpdateDao updateAppDao;

    public void addAppUpdateInfo(AppUpdateEntity appUpdateEntity) {
        this.updateAppDao.addAppUpdateInfo(appUpdateEntity);
    }

    public AppUpdateEntity getMaxVersion(String clientType) {
        return this.updateAppDao.getMaxVersion(clientType);
    }
}
