package com.qiaomu.websocket.bootstrap.handler;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.entity.PushMessage;
import com.qiaomu.common.reposity.PushMessageRepository;
import com.qiaomu.websocket.common.base.Handler;
import com.qiaomu.websocket.common.base.HandlerApi;
import com.qiaomu.websocket.common.base.HandlerService;
import com.qiaomu.websocket.common.bean.vo.SendServerVO;
import com.qiaomu.websocket.common.constant.Constans;
import com.qiaomu.websocket.common.constant.HttpConstant;
import com.qiaomu.websocket.common.constant.LogConstant;
import com.qiaomu.websocket.common.constant.NotInChatConstant;
import com.qiaomu.websocket.common.exception.NoFindHandlerException;
import com.qiaomu.websocket.common.utils.HttpUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * Create by UncleCatMySelf in 2018/12/06
 */
@Component
@ChannelHandler.Sharable
public class DefaultHandler extends Handler {

    private final Logger log = LoggerFactory.getLogger(DefaultHandler.class);

    @Autowired
    private  HandlerApi handlerApi;

    @Autowired
    private PushMessageRepository messageRepository;

    public  DefaultHandler defaultHandler;


    @PostConstruct
    public void init(){
        defaultHandler = this;
        defaultHandler.handlerApi= this.handlerApi;
    }

    @Override
    protected void webdoMessage(ChannelHandlerContext ctx, WebSocketFrame msg) {
        Channel channel = ctx.channel();
        HandlerService httpHandlerService;
        if (handlerApi instanceof HandlerService){
            httpHandlerService = (HandlerService)handlerApi;
        }else {
            throw new NoFindHandlerException(NotInChatConstant.NOT_HANDLER);
        }
        if (msg instanceof BinaryWebSocketFrame){
            //TODO 实现图片处理
        }
    }

    @Override
    protected void httpdoMessage(ChannelHandlerContext ctx, FullHttpRequest msg) {
        Channel channel = ctx.channel();
        HandlerService httpHandlerService;
        if (handlerApi instanceof HandlerService){
            httpHandlerService = (HandlerService)handlerApi;
        }else {
            throw new NoFindHandlerException(NotInChatConstant.NOT_HANDLER);
        }
        switch (HttpUtil.checkType(msg)){
            case HttpConstant.GETSIZE:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_GETSIZE);
                httpHandlerService.getSize(channel);
                break;
            case HttpConstant.SENDFROMSERVER:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDFROMSERVER);
                SendServerVO serverVO = null;
                try {
                    serverVO = HttpUtil.getToken(msg);
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage());
                }
                httpHandlerService.sendFromServer(channel,serverVO);
                break;
            case HttpConstant.GETLIST:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_GETLIST);
                httpHandlerService.getList(channel);
                break;
            case HttpConstant.SENDINCHAT:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDINCHAT);
                httpHandlerService.sendInChat(channel,msg);
                break;
            case HttpConstant.NOTFINDURI:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_NOTFINDURI);
                httpHandlerService.notFindUri(channel);
                break;
            default:
                System.out.println("为匹配"+msg);
                break;
        }
    }

    @Override
    protected void textdoMessage(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        Channel channel = ctx.channel();
        HandlerService handlerService;
        if (handlerApi instanceof HandlerService){
            handlerService = (HandlerService)handlerApi;
        }else{
            throw new NoFindHandlerException(NotInChatConstant.NOT_HANDLER);
        }
        Map<String,Object> maps = (Map) JSON.parse(msg.text());
        maps.put(Constans.TIME, new Date());
        switch ((String)maps.get(Constans.TYPE)){
            case Constans.LOGIN:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_LOGIN);
                handlerService.login(channel,maps);
                break;
            //针对个人，发送给自己
            case Constans.SENDME:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDME);
                handlerService.verify(channel,maps);
                handlerService.sendMeText(channel,maps);
                break;
            //针对个人，发送给某人
            case Constans.SENDTO:
                log.info(LogConstant.DefaultWebSocketHandler_SENDTO);
                handlerService.verify(channel,maps);
                handlerService.sendToText(channel,maps);
                break;
            //发送给群组
            case Constans.SENDGROUP:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDGROUP);
                handlerService.verify(channel,maps);
                handlerService.sendGroupText(channel,maps);
                break;
            //发送图片，发送给自己
            case Constans.SENDPHOTOTOME:
                log.info("图片到个人");
                handlerService.verify(channel,maps);
                handlerService.sendPhotoToMe(channel,maps);
                break;
            //消息发送成功返回的安全码
            case Constans.SECURITY_CODE:
                changeStatusMessageInfo(maps);
            default:
                break;
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(LogConstant.CHANNELACTIVE+ctx.channel().remoteAddress().toString()+LogConstant.CHANNEL_SUCCESS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
//        log.error("exception",cause);
        log.info(LogConstant.EXCEPTIONCAUGHT+ctx.channel().remoteAddress().toString()+LogConstant.DISCONNECT);
        ctx.close();
    }

    /**
     * 用户发送给信息成功的回调函数
     * 更改该条信息的状态
     * @param maps
     */
    private void changeStatusMessageInfo(Map<String,Object> maps){
        String ok = (String) maps.get("ok");
        Long messageId = (Long)maps.get("messageId");
        if(StringUtils.isNotBlank(ok) && messageId!=null){
            PushMessage message = messageRepository.getOne(messageId);
            message.setStatus(true);
            messageRepository.save(message);
        }

    }
}