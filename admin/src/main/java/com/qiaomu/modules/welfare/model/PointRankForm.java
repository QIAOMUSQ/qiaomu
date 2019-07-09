package com.qiaomu.modules.welfare.model;

/**
 * Created by wenglei on 2019/7/6.
 */
public class PointRankForm {
    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    private String point;
    private String loginName;
    private String realName;
}
