package com.qiaomu.push.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.push.dao.PushMessageDao;
import com.qiaomu.push.entity.PushMessage;
import com.qiaomu.push.service.PushMessageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-11-07 14:57
 */
@Service
public class PushMessageServiceImpl extends ServiceImpl<PushMessageDao, PushMessage> implements PushMessageService {


    @Override
    public List<PushMessage> findByReceivePhone(String phone) {
        return baseMapper.findByReceivePhone(new PushMessage(phone,false));
    }

}
