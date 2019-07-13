package com.qiaomu.modules.welfare.constant;

/**
 * Created by wenglei on 2019/7/13.
 */
public enum  Status {

    UNRUN("未执行"),RUN("执行中"), REVIEWING("待审核"), REVIEWED("审核中"), CANCEL("已取消"), UNGET("未领取"), COMPLETE("已完成");

    public String value;

    // 构造方法
    private Status(String value) {
        this.value = value;
    }

}
