package com.qiaomu.modules.workflow.enums;

/**
 * @author 李品先
 * @description:维修星级
 * @Date 2019-10-06 15:50
 */
public enum RepairsStar {
    oneStar("1","1星"),
    twoStar("2","2星"),
    threeStar("3","3星"),
    fourStar("4","4星"),
    fiveStar("5","5星");

    RepairsStar(String start, String startInfo) {
        this.start = start;
        this.startInfo = startInfo;
    }

    public static RepairsStar star(String type){
        for (RepairsStar star :values()){
            if (star.getStart().equals(type)){
                return  star;
            }
        }
        return null;
    }
    private String start;

    private String startInfo;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStartInfo() {
        return startInfo;
    }

    public void setStartInfo(String startInfo) {
        this.startInfo = startInfo;
    }
}
