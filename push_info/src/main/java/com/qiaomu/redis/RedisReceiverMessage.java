package com.qiaomu.redis;

import com.alibaba.fastjson.JSONArray;
import com.qiaomu.common.entity.PushMessage;
import com.qiaomu.websocket.bootstrap.channel.cache.WsCacheMapService;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 李品先
 * @description:redis接收消息
 * @Date 2019-07-14 23:03
 */

@Component
public class RedisReceiverMessage{


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private WsCacheMapService wsCacheMap;

    public void redisReceiverMessage(String message){
        PushMessage messages = JSONArray.parseObject(message,PushMessage.class);
        //预防消息丢失，从redis中获取信息
        Object object = redisTemplate.boundHashOps("message_history").get("history_"+messages.getPhone()+"_"+messages.getTime());
        if(object != null){
            redisTemplate.boundHashOps("message_history").delete("history_"+messages.getPhone()+"_"+messages.getTime());
        }
        detailMessage(messages);

    }

    private void detailMessage(PushMessage messages){

        /**
         * 1、根据数据类型判断推送类型（type）
         *  0:推送到个人
         *  1：推送到群组
         *  2：推送到社区
         *  4：推送到全部用户
         */
        String type = messages.getType();
        if("0".equals(type)){

        }else if("1".equals(type)){

        }else if("2".equals(type)){

        }else if("3".equals(type)){

        }else if("4".equals(type)){

        }


        /*** 2、若用户登陆*/
        /*** 3、获取用户通道*/
        /** * 4、查询推送对象是否在线 */
        /*** 5、若用户没用登陆，则存库 */

        if(wsCacheMap.hasToken(messages.getPhone())){
            //
            Channel channel = wsCacheMap.getByToken(messages.getPhone());

        }
    }

}
