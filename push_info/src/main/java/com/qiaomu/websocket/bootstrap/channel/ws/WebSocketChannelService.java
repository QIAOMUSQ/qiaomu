package com.qiaomu.websocket.bootstrap.channel.ws;


import com.google.gson.Gson;
import com.qiaomu.websocket.bootstrap.channel.cache.WsCacheMapService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *  聊天业务消息处理
 *
 */
@Service
public class WebSocketChannelService implements WsChannelService {

    @Autowired
    private WsCacheMapService wsCacheMap;

    @Override
    public void loginWsSuccess(Channel channel, String token) {
        try {
            //存储连接
            wsCacheMap.saveWs(token,channel);
            //存储入redis
            wsCacheMap.saveAd(channel.remoteAddress().toString(),token);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasOther(String otherOne) {
        return wsCacheMap.hasToken(otherOne);
    }

    @Override
    public Channel getChannel(String otherOne) {
        return wsCacheMap.getByToken(otherOne);
    }

    @Override
    public void close(Channel channel) {
        String token = wsCacheMap.getByAddress(channel.remoteAddress().toString());
        wsCacheMap.deleteAd(channel.remoteAddress().toString());
        wsCacheMap.deleteWs(token);
        channel.close();
    }

    @Override
    public boolean sendFromServer(Channel channel, Map<String, String> map) {
        Gson gson = new Gson();
        try {
            channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(map)));
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
