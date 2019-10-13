package com.qiaomu.modules.workflow.enums;

/**
 * @author 李品先
 * @description:维修数据字典
 * @Date 2019-10-06 15:22
 */
public enum RepairsTypeEnum {
    electric("0","电力报修"),
    waterSupply("1","供水报修"),
    coalGas("2","煤气报修"),
    homeRepair("3","房屋维修");

    private String type;

    private String repairsInfo;

    RepairsTypeEnum(String type, String repairsInfo) {
        this.type = type;
        this.repairsInfo = repairsInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRepairsInfo() {
        return repairsInfo;
    }

    public void setRepairsInfo(String repairsInfo) {
        this.repairsInfo = repairsInfo;
    }

    public static RepairsTypeEnum repairs(String type){
        for (RepairsTypeEnum statsEnum :values()){
            if (statsEnum.getType().equals(type)){
                return  statsEnum;
            }
        }
        return null;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}

