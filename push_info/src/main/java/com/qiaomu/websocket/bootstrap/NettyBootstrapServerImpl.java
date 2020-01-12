package com.qiaomu.websocket.bootstrap;


import com.qiaomu.websocket.auto.AutoConfig;
import com.qiaomu.websocket.common.bean.InitNetty;
import com.qiaomu.websocket.common.ip.IpUtils;
import com.qiaomu.websocket.common.utils.RemotingUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
@Service
public class NettyBootstrapServerImpl extends AbstractBootstrapServerImpl {

    private final Logger log = LoggerFactory.getLogger(NettyBootstrapServerImpl.class);

    @Autowired
    private InitNetty serverBean;

    private EventLoopGroup bossGroup;//主线程组---接受客户端连接，不做任何处理

    private EventLoopGroup workGroup;//从线程组---从线程组，接受从主线程组中传递的任务，并处理


    ServerBootstrap bootstrap;// 启动辅助类，为 netty 建立服务端的辅助类, 以 NIO为例

    /**
     * 服务开启
     */
    public void start() {
        initEventPool();
        bootstrap.group(bossGroup, workGroup)//设置主从线程组
                .channel(useEpoll()?EpollServerSocketChannel.class:NioServerSocketChannel.class)//设置NIO双向通道
                .option(ChannelOption.SO_REUSEADDR, serverBean.isReuseaddr())
                .option(ChannelOption.SO_BACKLOG, serverBean.getBacklog())
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.SO_RCVBUF, serverBean.getRevbuf())
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {//子处理器，用于处理workGroup提交过来的信息
                        initHandler(ch.pipeline(),serverBean);
                    }
                })
                .childOption(ChannelOption.TCP_NODELAY, serverBean.isNodelay())
                .childOption(ChannelOption.SO_KEEPALIVE, serverBean.isKeepalive())
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        bootstrap.bind(IpUtils.getHost(),serverBean.getPort())
                .addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                log.info("服务端启动成功【" + IpUtils.getHost() + ":" + serverBean.getPort() + "】");
                //将本服务的地址信息存入自动配置类中
                AutoConfig.address = IpUtils.getHost()+":"+serverBean.getPort();
                //RedisConfig.getInstance();
            }else{
                log.info("服务端启动失败【" + IpUtils.getHost() + ":" + serverBean.getPort() + "】");}
        });
    }
    /**
     * 初始化EnentPool 参数
     */
    private void  initEventPool(){
        bootstrap= new ServerBootstrap();
        //当是Linux服务器，并且Epoll是可用的
        if(useEpoll()){
            bossGroup = new EpollEventLoopGroup(serverBean.getBossThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);
                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_BOSS_" + index.incrementAndGet());
                }
            });
            workGroup = new EpollEventLoopGroup(serverBean.getWorkerThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);
                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_WORK_" + index.incrementAndGet());
                }
            });

        } else {
            bossGroup = new NioEventLoopGroup(serverBean.getBossThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);
                public Thread newThread(Runnable r) {
                    return new Thread(r, "BOSS_" + index.incrementAndGet());
                }
            });
            workGroup = new NioEventLoopGroup(serverBean.getWorkerThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);
                public Thread newThread(Runnable r) {
                    return new Thread(r, "WORK_" + index.incrementAndGet());
                }
            });
        }
    }

    /**
     * 关闭资源
     */
    public void shutdown() {
        if(workGroup!=null && bossGroup!=null ){
            try {
                bossGroup.shutdownGracefully().sync();// 优雅关闭
                workGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                log.error("服务端关闭资源失败【" + IpUtils.getHost() + ":" + serverBean.getPort() + "】");
            }
        }
    }

    private boolean useEpoll() {
        return RemotingUtil.isLinuxPlatform() && Epoll.isAvailable();
    }

}
