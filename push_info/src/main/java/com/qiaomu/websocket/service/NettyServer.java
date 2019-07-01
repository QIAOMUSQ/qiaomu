package com.qiaomu.websocket.service;

import com.qiaomu.websocket.handler.ChildChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author 李品先
 * @description:
 * @Date 2019-06-24 16:24
 */
@Service
public class NettyServer {

    public static void main(String[] args) {
        new NettyServer().run();
    }

    //@PostConstruct
    public void initNetty(){
        new Thread(){
            public void run() {
                new NettyServer().run();
            }
        }.start();
    }
    public void run(){
        System.out.println("===========================Netty端口启动========");
        // Boss线程：由这个线程池提供的线程是boss种类的，用于创建、连接、绑定socket， （有点像门卫）然后把这些socket传给worker线程池。
        // 主线程组 在服务器端每个监听的socket都有一个boss线程来处理。在客户端，只有一个boss线程来处理所有的socket。
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 从线程组  Worker线程：Worker线程执行所有的异步I/O，即处理操作
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            // netty服务器的创建，ServiceBootStrap是启动类
            // ServerBootstrap 启动NIO服务的辅助启动类,负责初始话netty服务器，并且开始监听端口的socket请求
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)//设置主从线程组
                    .channel(NioServerSocketChannel.class)//设置NIO双向通道
                    .childHandler(new ChildChannelHandler());//自处理器，用于处理workGroup

            // ChildChannelHandler 对出入的数据进行的业务操作,其继承ChannelInitializer
            System.out.println("服务端开启等待客户端连接 ... ...");
            Channel ch = b.bind(8082).sync().channel();
            ch.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
