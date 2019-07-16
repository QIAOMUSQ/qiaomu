package com.qiaomu.config;

import com.qiaomu.redis.RedisReceiverMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author 李品先
 * @description:redis消息监听config
 * @Date 2019-07-14 22:54
 */
@Configuration
public class RedisListenerConfig {

    /**
     * redis消息监听器容器
     * @param connectionFactory
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);//EXISTS
        container.addMessageListener(listenerAdapter, new PatternTopic("message"));
        return container;
    }
    /**
     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
     * @param redisReceiverMessage
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(RedisReceiverMessage redisReceiverMessage) {
        System.out.println("消息适配器进来了");
        return new MessageListenerAdapter(redisReceiverMessage, "redisReceiverMessage");
    }

}
