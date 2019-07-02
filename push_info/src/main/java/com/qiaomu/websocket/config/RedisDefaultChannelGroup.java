package com.qiaomu.websocket.config;

import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;

import java.util.function.Predicate;

/**
 * @author 李品先
 * @description:自定义NettyChannelGroup类
 * @Date 2019-07-01 19:05
 */
public class RedisDefaultChannelGroup extends DefaultChannelGroup {

    public RedisDefaultChannelGroup(EventExecutor executor) {
        super(executor);
    }

    public RedisDefaultChannelGroup(String name, EventExecutor executor) {
        super(name, executor);
    }
}
