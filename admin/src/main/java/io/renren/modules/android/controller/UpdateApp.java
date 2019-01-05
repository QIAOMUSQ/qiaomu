package io.renren.modules.android.controller;

import io.renren.common.utils.R;
import io.renren.modules.qiaomu.bussiness.UpdateAppBussiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by wenglei on 2018/11/12.
 */
@RestController
@RequestMapping("sys/UpdateApp")
public class UpdateApp {
    @Autowired
    private UpdateAppBussiness updateAppBussiness;

    /**
     * 列表
     */
    @RequestMapping("/uploadFile")
    public R list(HttpServletRequest request){
        R result = updateAppBussiness.uploadFile(request);

        return result;
    }

    @RequestMapping("/isNeedUpdate")
    public R isNeedUpdate(String version){
        R result = updateAppBussiness.isNeedUpdate(version);

        return result;
    }

}
