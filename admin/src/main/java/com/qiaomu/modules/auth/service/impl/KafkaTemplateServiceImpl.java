package com.qiaomu.modules.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiaomu.modules.auth.service.KafkaTemplateService;
import com.qiaomu.modules.workflow.VO.PushMessageVO;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 李品先
 * @description:
 * @Date 2019-11-08 16:44
 */
@Service
public class KafkaTemplateServiceImpl implements KafkaTemplateService {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @Override
    public void loginTopic(SysUserEntity sysUserEntity) {
        /*Thread thread = new Thread(){
            public void run(){

            }
        };
        thread.start();*/
        kafkaTemplate.send("userLoginTopic", JSON.toJSONString(sysUserEntity));

    }

    @Override
    public void pushInvitationInfo(PushMessageVO message) {
        kafkaTemplate.send("invitationTopic", JSON.toJSONString(message));
    }

    @Override
    public void pushRepairsInfo(PushMessageVO message) {
        kafkaTemplate.send("repairsTopic", JSON.toJSONString(message));
    }
}
