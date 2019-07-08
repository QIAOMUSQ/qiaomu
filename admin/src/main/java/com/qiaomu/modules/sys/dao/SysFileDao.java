package com.qiaomu.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.sys.entity.SysFileEntity;

/**
 * @author 李品先
 * @description:
 * @Date 2019-01-15 18:43
 */
public interface SysFileDao extends BaseMapper<SysFileEntity> {

    void insertInfo(SysFileEntity entity);
}
