package com.qiaomu.websocket.users;


/**
 * Created by MySelf on 2019/1/3.
 */

public enum  FromServerServiceImpl{

    TYPE1("1","【系统通知】连接失败"),
    TYPE2("2","【系统通知】连接成功");

    private String code;

    private String message;

    FromServerServiceImpl(String code, String message){
        this.code = code;
        this.message = message;
    }

       public String getCode() {
        return code;
    }

    public static String findByCode(Object code) {
        for (FromServerServiceImpl item: FromServerServiceImpl.values()) {
            if (item.code == code){
                return item.message;
            }
        }
        return null;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
