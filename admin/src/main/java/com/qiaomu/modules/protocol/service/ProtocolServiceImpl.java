package com.qiaomu.modules.protocol.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.modules.protocol.dao.ProtocolDao;
import com.qiaomu.modules.protocol.entity.ProtocolEntity;
import org.springframework.stereotype.Service;

/**
 * @author 李品先
 * @description:
 * @Date 2020-03-22 11:35
 */
@Service
public class ProtocolServiceImpl extends ServiceImpl<ProtocolDao,ProtocolEntity> implements ProtocolService {
}
