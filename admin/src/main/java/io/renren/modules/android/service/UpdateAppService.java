package io.renren.modules.android.service;

import io.renren.modules.android.dao.UpdateAppDao;
import io.renren.modules.android.entity.AppUpdateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wenglei on 2018/11/21.
 */
@Service
public class UpdateAppService {
    @Autowired
    private UpdateAppDao updateAppDao;

    public void addAppUpdateInfo(AppUpdateEntity appUpdateEntity){
        updateAppDao.addAppUpdateInfo(appUpdateEntity);


    }

    public AppUpdateEntity getMaxVersion(){
        return updateAppDao.getMaxVersion();
    }

}
