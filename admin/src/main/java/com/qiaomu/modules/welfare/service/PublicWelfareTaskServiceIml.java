package com.qiaomu.modules.welfare.service;

import com.qiaomu.modules.welfare.dao.WelfareTaskDao;
import com.qiaomu.modules.welfare.entity.TaskEntity;
import com.qiaomu.modules.welfare.entity.TaskPublishUserEntity;
import com.qiaomu.modules.welfare.entity.TaskRecevieUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wenglei on 2019/6/16.
 */
@Service
public class PublicWelfareTaskServiceIml implements PublicWelfareTaskService {
    @Autowired
    private WelfareTaskDao welfareTaskDao;
    @Override
    public String newTask(TaskEntity taskEntity) {
        welfareTaskDao.newTask(taskEntity);
        return welfareTaskDao.queryTaskByName(taskEntity.getServiceName()).getServiceId();

    }

    @Override
    public void newTaskUser(TaskPublishUserEntity taskUserEntity) {
        welfareTaskDao.newTaskUser(taskUserEntity);
    }

    @Override
    public void updateTaskUser(TaskPublishUserEntity taskUserEntity) {
        welfareTaskDao.updateTaskUser(taskUserEntity);
    }

    @Override
    public void updateTask(TaskEntity TaskEntity) {
        welfareTaskDao.updateTask(TaskEntity);
    }

    @Override
    public void newTaskRecevieUser(TaskRecevieUserEntity taskRecevieUserEntity) {
        welfareTaskDao.newTaskRecevieUser(taskRecevieUserEntity);
    }

    @Override
    public void updateTaskRecevieUser(TaskRecevieUserEntity taskRecevieUserEntity) {
        welfareTaskDao.updateTaskRecevieUser(taskRecevieUserEntity);
    }

    @Override
    public TaskEntity queryTask(String serviceId) {
        return welfareTaskDao.queryTask(serviceId);
    }

    @Override
    public TaskPublishUserEntity queryPublishUserTask(String serviceId) {
        return  welfareTaskDao.queryPublishUserTask(serviceId);
    }

    @Override
    public TaskRecevieUserEntity queryRecevieUserTask(String serviceId) {
        return welfareTaskDao.queryRecevieUserTask(serviceId);
    }

    @Override
    public TaskPublishUserEntity queryPublishUserTaskLast(String serviceId) {
        return welfareTaskDao.queryPublishUserTaskLast(serviceId);
    }
}
