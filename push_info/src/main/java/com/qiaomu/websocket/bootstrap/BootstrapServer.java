package com.qiaomu.websocket.bootstrap;


import com.qiaomu.websocket.common.bean.InitNetty;

/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
public interface BootstrapServer {

    void shutdown();

    //void setServerBean(InitNetty serverBean);

    void start();


}
