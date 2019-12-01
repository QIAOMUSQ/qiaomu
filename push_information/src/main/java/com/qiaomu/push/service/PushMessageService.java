package com.qiaomu.push.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.push.entity.PushMessage;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-11-07 14:57
 */
public interface PushMessageService extends IService<PushMessage>{

    List<PushMessage> findByReceivePhone(String phone);


}
