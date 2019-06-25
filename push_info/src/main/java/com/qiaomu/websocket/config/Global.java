package com.qiaomu.websocket.config;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author 李品先
 * @description:
 * @Date 2019-06-24 22:11
 */
public class Global {
    /**
     * 存储每个客户端接入景来的channel对象
     */
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
