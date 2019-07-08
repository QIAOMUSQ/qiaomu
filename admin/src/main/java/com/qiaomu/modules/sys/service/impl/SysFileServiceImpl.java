package com.qiaomu.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.modules.sys.dao.SysFileDao;
import com.qiaomu.modules.sys.entity.SysFileEntity;
import com.qiaomu.modules.sys.service.SysFileService;
import org.springframework.stereotype.Service;

/**
 * @author 李品先
 * @description:
 * @Date 2019-01-15 18:46
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileDao, SysFileEntity> implements SysFileService {



    @Override
    public void insertInfo(SysFileEntity fileEntity) {
         this.baseMapper.insertInfo(fileEntity);
    }
}
