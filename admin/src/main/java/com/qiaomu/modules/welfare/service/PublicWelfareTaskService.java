package com.qiaomu.modules.welfare.service;

import com.qiaomu.modules.welfare.entity.PointEntity;
import com.qiaomu.modules.welfare.entity.TaskEntity;
import com.qiaomu.modules.welfare.entity.TaskPublishUserEntity;
import com.qiaomu.modules.welfare.entity.TaskRecevieUserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
    List<TaskEntity> queryPublishUserServices(String publishUserId);
    List<TaskEntity> queryRecevieUserServices(String receiveUserId);
    List<TaskEntity> queryAllunReviewTask(String receiveUserId);
    void updatePonit(PointEntity pointEntity);
    Integer selectPonitByUserId(String userId);
    List<PointEntity> selectTopPointUser();
}
