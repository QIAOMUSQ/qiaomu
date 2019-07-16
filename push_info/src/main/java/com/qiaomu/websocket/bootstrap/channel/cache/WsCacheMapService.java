package com.qiaomu.websocket.bootstrap.channel.cache;


import com.qiaomu.websocket.auto.AutoConfig;
import com.qiaomu.websocket.common.bean.InitNetty;
import com.qiaomu.websocket.common.constant.NotInChatConstant;
import com.qiaomu.websocket.common.exception.NotFindLoginChannlException;
import com.qiaomu.websocket.common.utils.RedisUtil;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket链接实例本地存储
 * Created by MySelf on 2018/11/26.
 */
@Service
public class WsCacheMapService {

    @Autowired
    private InitNetty initNetty;
    /**
     * 存储用户标识与链接实例
     */
    private  Map<String,Channel> maps = new ConcurrentHashMap<String,Channel>();

    /**
     * 存储链接地址与用户标识
     */
    private final static Map<String,String> addMaps = new ConcurrentHashMap<>();

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 是否启动分布式
     */

    private final static String address = AutoConfig.address;

    /**
     * 存储链接
     * @param token {@link String} 用户标签
     * @param channel {@link Channel} 链接实例
     */
    public  void saveWs(String token,Channel channel){
        maps.put(token,channel);
        if (initNetty.getDistributed()){
            redisTemplate.opsForValue().set(token,RedisUtil.convertMD5(address,token));
        }
    }

    /**
     * 存入redis中，存储登录信息
     * @param address 登录地址
     * @param token 用户标签
     */
    public  void saveAd(String address,String token){
        addMaps.put(address, token);
    }

    /**
     * 获取链接数据
     * @param token {@link String} 用户标识
     * @return {@link Channel} 链接实例
     */
    public  Channel getByToken(String token){
        //若是分布式
        if (initNetty.getDistributed()){
           if (!maps.containsKey(token)){
               //转分布式发送
               return null;
           }
        }
        return maps.get(token);
    }

    /**
     * 获取对应token标签
     * @param address {@link String} 链接地址
     * @return {@link String}
     */
    public  String getByAddress(String address){
        return addMaps.get(address);
    }

    /**
     * 删除链接数据
     * @param token {@link String} 用户标识
     */
    public  void deleteWs(String token){
        try {
            maps.remove(token);
            if (initNetty.getDistributed()){
                redisTemplate.delete(token);
            }
        }catch (NullPointerException e){
            throw new NotFindLoginChannlException(NotInChatConstant.Not_Login);
        }
    }

    /**
     * 删除链接地址
     * @param address
     */
    public  void deleteAd(String address){
        addMaps.remove(address);
    }

    /**
     * 获取链接数
     * @return {@link Integer} 链接数
     */
    public  Integer getSize(){
        if (initNetty.getDistributed()){
            return redisTemplate.keys("*").size();
        }
        return maps.size();
    }

    /**
     * 判断是否存在链接账号
     * @param token {@link String} 用户标识
     * @return {@link Boolean} 是否存在
     */
    public  boolean hasToken(String token){
        if (initNetty.getDistributed()){

            return redisTemplate.hasKey(token);
        }
        return maps.containsKey(token);
    }

    /**
     * 获取在线用户标签列表
     * @return {@link Set} 标识列表
     */
    public  Set<String> getTokenList(){
        if (initNetty.getDistributed()){
            return redisTemplate.keys("*");
        }
        Set keys = maps.keySet();
        return keys;
    }

    public String getByJedis(String token) {
        return (String) redisTemplate.opsForValue().get(token);
    }
}
