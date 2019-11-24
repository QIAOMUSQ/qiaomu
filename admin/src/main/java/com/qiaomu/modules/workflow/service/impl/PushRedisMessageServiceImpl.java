package com.qiaomu.modules.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiaomu.modules.workflow.service.PushRedisMessageService;
import com.qiaomu.modules.workflow.VO.PushMessageVO;
import com.qiaomu.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 李品先
 * @description:
 * @Date 2019-07-14 22:37
 */
@Service
public class PushRedisMessageServiceImpl implements PushRedisMessageService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    private SysUserService userService;
    /**
     * type:类型
     *  0:推送到个人
     *  1：推送到群组
     *  2：推送到社区
     *  3：推送到全部用户
     *  type=0时，接收人phone一定不为空
     * @param message
     */
    @Override
    public void pushMessageToRedis(PushMessageVO message) {
       // byte[] msg =jackson2JsonRedisSerializer.serialize(message);
        stringRedisTemplate.convertAndSend("message",JSON.toJSONString(message));
        redisTemplate.boundHashOps("message_history").put("history_"+message.getReceivePhone()+"_"+message.getTime().replace(" ","").replace("-","").replace(":",""),JSON.toJSONString(message));
    }


    /**
     *
     * @param pushUserId 用户Id
     * @param receiveIdS 被推送人Id
     * @param infoType 推送信息类型（比如：爱心银行推送，车位推送，报修申请推送）
     * @param type 推送类型（0：推送到个人，2：推送到社区）
     * @param message 信息
     * @param communityId 社区id
     */
    @Override
    public void pushMessage(Long pushUserId, String receiveIdS, String infoType, String type, String message, Long communityId) {
        /*String pushPhone = userService.queryById(pushUserId).getUsername();
        if(!type.equals("2")&& StringUtils.isNotBlank(receiveIdS)){
            String[] ids = receiveIdS.split(",");
            for (String id : ids){
                String receivePhone  = userService.queryById(Long.valueOf(id)).getUsername();
                PushMessage data = new PushMessage(receivePhone,type,infoType, DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),message,pushPhone,communityId);
                stringRedisTemplate.convertAndSend("message",JSON.toJSONString(data));
                redisTemplate.boundHashOps("message_history").put("history_"+data.getReceivePhone()+"_"+data.getTime().replace(" ","").replace("-","").replace(":",""),JSON.toJSONString(data));
            }
        }else if (type.equals("2")){
            PushMessage data = new PushMessage(type,infoType, DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),communityId,message,pushPhone);
            stringRedisTemplate.convertAndSend("message",JSON.toJSONString(data));
            redisTemplate.boundHashOps("message_history").put("history_"+pushPhone+"_"+data.getTime().replace(" ","").replace("-","").replace(":",""),JSON.toJSONString(data));
        }*/
    }
}
