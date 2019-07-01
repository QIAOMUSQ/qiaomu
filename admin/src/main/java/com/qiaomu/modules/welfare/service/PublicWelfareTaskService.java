package com.qiaomu.modules.welfare.service;

import com.qiaomu.modules.welfare.entity.TaskEntity;
import com.qiaomu.modules.welfare.entity.TaskPublishUserEntity;
import com.qiaomu.modules.welfare.entity.TaskRecevieUserEntity;
import org.springframework.stereotype.Service;

/**
 * Created by wenglei on 2019/6/16.
 */

public interface PublicWelfareTaskService {
    String newTask(TaskEntity taskEntity);
    void newTaskUser(TaskPublishUserEntity taskUserEntity);
    void updateTaskUser(TaskPublishUserEntity taskUserEntity);
    void updateTask(TaskEntity TaskEntity);
    void newTaskRecevieUser(TaskRecevieUserEntity taskRecevieUserEntity);
    void updateTaskRecevieUser(TaskRecevieUserEntity taskRecevieUserEntity);
    TaskEntity queryTask(String serviceId);
    TaskPublishUserEntity queryPublishUserTask(String serviceId);
    TaskRecevieUserEntity queryRecevieUserTask(String serviceId);
    TaskPublishUserEntity queryPublishUserTaskLast(String serviceId);
}
