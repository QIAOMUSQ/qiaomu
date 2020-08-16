package com.qiaomu.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.modules.sys.dao.AppErrorEventLogDao;
import com.qiaomu.modules.sys.dao.AppErrorLogDao;
import com.qiaomu.modules.sys.entity.AppErrorEventLog;
import com.qiaomu.modules.sys.entity.AppErrorLog;
import com.qiaomu.modules.sys.service.AppErrorLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2020-08-16 18:58
 */
@Slf4j
@Service
public class AppErrorLogServiceImpl extends ServiceImpl<AppErrorLogDao, AppErrorLog> implements AppErrorLogService {

    @Resource
    private AppErrorEventLogDao appErrorEventLogDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(AppErrorLog appErrorLog) {
        appErrorLog.setTimeStamp(new Date());
        baseMapper.insert(appErrorLog);
        List<AppErrorEventLog> eventLog = appErrorLog.getEventLogList();
       eventLog.forEach(m->{
           m.setErrorLogId(appErrorLog.getId());
           appErrorEventLogDao.insert(m);
       });
    }

    @Override
    public List<AppErrorLog> selectLogList(EntityWrapper<AppErrorLog> wrapper) {
        List<AppErrorLog> list = baseMapper.selectList(wrapper);
        for (AppErrorLog log : list){
            log.setEventLogList(appErrorEventLogDao.selectListByLogId(log.getId()));
        }
        return list;
    }
}
