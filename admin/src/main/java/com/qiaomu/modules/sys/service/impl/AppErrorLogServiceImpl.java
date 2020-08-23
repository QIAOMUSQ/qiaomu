package com.qiaomu.modules.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.modules.sys.dao.AppErrorEventLogDao;
import com.qiaomu.modules.sys.dao.AppErrorLogDao;
import com.qiaomu.modules.sys.entity.AppErrorEventLog;
import com.qiaomu.modules.sys.entity.AppErrorLog;
import com.qiaomu.modules.sys.service.AppErrorLogService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Value("${error-file}")
    private String errorFile;
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
        File file = new File(errorFile);
        if (!file.exists()) file.mkdirs();
        File newFile = new File(errorFile + appErrorLog.getLoginName()+ DateTime.now().toString("yyyy-MM-dd")+".log");
        OutputStream out= null;//追加内容
        try {
            out = new FileOutputStream(newFile,true);
            byte[] b = JSON.toJSONString(appErrorLog).getBytes();
            for(int i=0;i<b.length;i++){
                out.write(b[i]);
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AppErrorLog> selectLogList(EntityWrapper<AppErrorLog> wrapper) {
        List<AppErrorLog> list = baseMapper.selectList(wrapper);
        for (AppErrorLog log : list){
            log.setEventLogList(appErrorEventLogDao.selectListByLogId(log.getId()));
        }
        return list;
    }

    @Override
    public List<File> getFileList() {
       List<File> list =  getList(errorFile);
        return list;
    }

    private List<File> getList(String patha){
        String path=patha;
        File file=new File(path);
        File[] tempList = file.listFiles();
        System.out.println("该目录下对象个数："+tempList.length);
        List<File> list  = new ArrayList<>();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                list.add(tempList[i]);
                System.out.println("文     件："+tempList[i]);
            }
            if (tempList[i].isDirectory()) {
                System.out.println("文件夹："+tempList[i].getPath());

                //递归：
                getList(tempList[i].getPath());
            }
        }
        return list;
    }
}
