package com.qiaomu.push.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.push.entity.PushMessage;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-11-07 14:55
 */
public interface PushMessageDao extends BaseMapper<PushMessage> {

    List<PushMessage> findByReceivePhone(PushMessage pushMessage);
}
