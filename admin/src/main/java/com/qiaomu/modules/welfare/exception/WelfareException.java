package com.qiaomu.modules.welfare.exception;

/**
 * Created by wenglei on 2019/5/26.
 */
public class WelfareException extends RuntimeException{
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private String msg;
    private int code = 1001;

    public WelfareException(String msg) {
        super(msg);
        this.msg = msg;
    }
    public WelfareException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
