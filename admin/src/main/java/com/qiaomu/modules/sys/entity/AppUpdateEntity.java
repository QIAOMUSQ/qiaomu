package com.qiaomu.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * Created by wenglei on 2018/11/21.
 */
@TableName("pluto_updateAppinfo")
public class AppUpdateEntity {
    private String id;

    private String appVersion;

    private String appUrl;

    private String updateType;

    private String createdAs;

    private String updatedAs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getCreatedAs() {
        return createdAs;
    }

    public void setCreatedAs(String createdAs) {
        this.createdAs = createdAs;
    }

    public String getUpdatedAs() {
        return updatedAs;
    }

    public void setUpdatedAs(String updatedAs) {
        this.updatedAs = updatedAs;
    }
}
