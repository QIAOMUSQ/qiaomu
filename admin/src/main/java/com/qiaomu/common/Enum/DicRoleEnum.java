package com.qiaomu.common.Enum;

/**
 * @author 李品先
 * @description:用户权限
 * @Date 2019-08-31 18:07
 */
public enum DicRoleEnum {
    ADMIN("1", "0"),//超級管理員
    COMPANY_ADMIN("2", "1"),//物业管理员
    COMMUNITY_ADMIN("3", "2"),//社区工作员
    OWNERS("4", "3"),//物业业主
    VISITOR("5", "4");//游客

    private String name;
    private String value;

    DicRoleEnum(String name, String value) {
        this.name = name;
        this.value = value;
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

    public static DicRoleEnum stateOf(String index){
        for (DicRoleEnum statsEnum :values()){
            if (statsEnum.getValue() == index){
                return  statsEnum;
            }
        }
        return null;
    }
}
