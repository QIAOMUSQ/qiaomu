package io.renren.modules.android.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Created by wenglei on 2018/11/21.
 */
@Data
@TableName("pluto_updateAppinfo")
public class AppUpdateEntity {
    private String id;

    private String appVersion;

    private String appUrl;

    private String updateType;

    private String createdAs;

    private String updatedAs;
}
