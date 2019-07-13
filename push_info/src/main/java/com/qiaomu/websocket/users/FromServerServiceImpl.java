package com.qiaomu.websocket.users;


import com.qiaomu.websocket.bootstrap.channel.http.FromServerService;

/**
 * Created by MySelf on 2019/1/3.
 */
public enum  FromServerServiceImpl implements FromServerService {

    TYPE1(1,"【系统通知】连接失败"),
    TYPE2(2,"【系统通知】连接成功");

    private Integer code;

    private String message;

    FromServerServiceImpl(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String findByCode(Object code) {
        Integer codes = (Integer)code;
        for (FromServerServiceImpl item: FromServerServiceImpl.values()) {
            if (item.code == codes){
                return item.message;
            }
        }
        return null;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
