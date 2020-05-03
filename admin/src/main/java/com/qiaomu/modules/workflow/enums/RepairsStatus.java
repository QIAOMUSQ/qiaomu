package com.qiaomu.modules.workflow.enums;

/**
 * @author 李品先
 * @description:维修状态
 * @Date 2019-10-06 15:57
 */
public enum RepairsStatus {
    commit("0","已提交"),
    assign("1","已分派"),
    finish("2","维修结束"),
    invalid("4","维修撤销"),
    finishAndClose("5","解决并关闭"),
    overTime("6","延迟解决"),
    noSolve("7","无法解决，转外部服务");
    private String status;

    private String statusInfo;

    RepairsStatus(String status, String statusInfo) {
        this.status = status;
        this.statusInfo = statusInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public static RepairsStatus status(String type){
        for (RepairsStatus status :values()){
            if (status.getStatus().equals(type)){
                return  status;
            }
        }
        return null;
    }


}
