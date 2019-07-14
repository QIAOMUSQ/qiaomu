package com.qiaomu.websocket.auto;


import com.qiaomu.common.reposity.PushMessageRepository;
import com.qiaomu.websocket.bootstrap.BootstrapServer;
import com.qiaomu.websocket.bootstrap.NettyBootstrapServerImpl;
import com.qiaomu.websocket.common.bean.InitNetty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * InChat项目启动服务
 * Create by UncleCatMySelf in 2018/12/06
 **/
@Service
public class InitServer {

    /** 静态化处理，保证唯一，确保用户启动的是他自己指定的，不是框架的
     *  一个数据配置集合
     */
    @Autowired
    private  InitNetty initNetty;

    /** netty服务器启动切面 */
    @Autowired
    private BootstrapServer bootstrapServer;

    @Autowired
    private PushMessageRepository pushMessageRepository;

    /**
     * 主要还是这个{@link NettyBootstrapServerImpl},实例化想要的netty配置服务
     */
    public  void open(){
        if(initNetty!=null){
       /*     bootstrapServer = new NettyBootstrapServer();
           // bootstrapServer.setServerBean(serverBean);*/
            bootstrapServer.start();
        }
    }

    /**
     * 关闭服务
     */
    public void close(){
        if(bootstrapServer!=null){
            bootstrapServer.shutdown();
        }
    }

}
