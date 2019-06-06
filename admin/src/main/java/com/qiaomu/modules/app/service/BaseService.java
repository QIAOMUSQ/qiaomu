package com.qiaomu.modules.app.service;

import com.qiaomu.modules.sys.dao.AppUpdateDao;
import com.qiaomu.modules.sys.entity.AppUpdateEntity;
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

    public AppUpdateEntity getMaxVersion() {
        return this.updateAppDao.getMaxVersion();
    }
}
