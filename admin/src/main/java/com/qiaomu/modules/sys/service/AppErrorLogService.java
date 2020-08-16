package com.qiaomu.modules.sys.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.modules.sys.entity.AppErrorLog;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2020-08-16 18:57
 */
public interface AppErrorLogService extends IService<AppErrorLog> {
    void save(AppErrorLog appErrorLog);

    List<AppErrorLog> selectLogList(EntityWrapper<AppErrorLog> wrapper);
}
