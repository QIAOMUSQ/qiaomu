package com.qiaomu.common.Enum;

/**
 * @author 李品先
 * @description:社区角色
 * @Date 2019-10-30 10:01
 */
public enum CommunityRoleType {
    TOURIST ("游客","4"),
    PROPRIETOR("业主","3"),
    WORKER("工作人员","2"),
    ADMINISTRATOR("物业管理员","1"),
    SUPER_ADMINISTRATOR("超级管理员","0");

    private String name ;
    private String value;


    CommunityRoleType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static CommunityRoleType roleType(String value){
        for (CommunityRoleType role :values()){
            if (role.getValue() == value){
                return  role;
            }
        }
        return null;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    CommunityRoleType() {
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
