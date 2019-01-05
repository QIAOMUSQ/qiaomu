package io.renren.modules.android.bussiness;

import io.renren.common.utils.R;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wenglei on 2018/11/12.
 */
public interface UpdateAppBussiness {
    public R uploadFile(HttpServletRequest request);

    public R downloadFile(HttpServletRequest request);


    R isNeedUpdate(String version);
}
