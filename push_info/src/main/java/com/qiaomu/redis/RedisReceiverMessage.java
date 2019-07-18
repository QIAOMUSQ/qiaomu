package com.qiaomu.redis;

import com.alibaba.fastjson.JSONArray;
import com.qiaomu.common.entity.PushMessage;
import com.qiaomu.common.entity.SysUserEntity;
import com.qiaomu.common.entity.UserExtend;
import com.qiaomu.common.reposity.PushMessageRepository;
import com.qiaomu.common.reposity.SysUserEntityRepository;
import com.qiaomu.common.reposity.UserExtendRepository;
import com.qiaomu.websocket.bootstrap.channel.cache.WsCacheMapService;
import com.qiaomu.websocket.common.base.HandlerServiceImpl;
import com.qiaomu.websocket.common.constant.Constans;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private HandlerServiceImpl handlerService;

    @Autowired
    private PushMessageRepository messageRepository;

    @Autowired
    private UserExtendRepository userExtendRepository;

    @Autowired
    private SysUserEntityRepository  userEntityRepository;

    public void redisReceiverMessage(String message){
        PushMessage messages = JSONArray.parseObject(message,PushMessage.class);
        BoundHashOperations<String, String, Object> boundHashOps = redisTemplate.boundHashOps("message_history");
        //预防消息丢失，从redis中获取信息
        Object object = boundHashOps.get("history_"+messages.getPhone()+"_"+messages.getTime().replace(" ","").replace("-","").replace(":",""));
        if(object != null){
            redisTemplate.boundHashOps("message_history").delete("history_"+messages.getPhone()+"_"+messages.getTime().replace(" ","").replace("-","").replace(":",""));
        }
        detailMessage(messages);

    }

    private void detailMessage(PushMessage messages){

        /**
         * 1、根据数据类型判断推送类型（type）
         *  0: 推送到个人
         *  1：推送到群组
         *  2：推送到社区 communityId 不为空
         *  3：推送到全部用户
         */
        String type = messages.getType();
        if("0".equals(type)){
            messages.setCreateTime(new Date());
            pushOnePeople(messages);
        }else if("1".equals(type)){

        }else if("2".equals(type)){
            //获取该社区下人员认证信息
            List<UserExtend> userList =  userExtendRepository.findByCommunityId(messages.getCommunityId());
            for(UserExtend user:userList){
              //  messages.setPhone(user.get);
                SysUserEntity userEntity = userEntityRepository.findByUserId(user.getUserId());
                PushMessage mes = new PushMessage();
                mes.setPhone(userEntity.getUsername());
                mes.setCreateTime(new Date());
                mes.setUserPhone(messages.getUserPhone());
                mes.setTime(mes.getTime());
                mes.setInfoType(messages.getInfoType());
                mes.setCommunityId(messages.getCommunityId());
                mes.setMessage(messages.getMessage());
                pushOnePeople(mes);
            }
        }else if("3".equals(type)){

        }
    }

    private void pushOnePeople(PushMessage messages){
        messages.setStatus(false);
        /*** 6、推送信息存库，方便返回数据校验 */
        messageRepository.save(messages);
        /*** 2、若用户登陆*/
        if(wsCacheMap.hasToken(messages.getPhone())){
            /*** 3、获取用户通道*/
            Channel channel = wsCacheMap.getByToken(messages.getPhone());
            /** 4、组装数据 */
            Map<String, Object> maps = new HashMap<>();
            maps.put(Constans.ONE,messages.getPhone());
            maps.put(Constans.VALUE,messages);
            maps.put(Constans.TOKEN,messages.getUserPhone());
            /*** 5、推送出去 */
            handlerService.sendToText(channel,maps);
        }
    }

}
