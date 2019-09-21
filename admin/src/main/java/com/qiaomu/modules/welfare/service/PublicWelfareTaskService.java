package com.qiaomu.modules.welfare.service;

import com.qiaomu.modules.welfare.entity.PointEntity;
import com.qiaomu.modules.welfare.entity.TaskEntity;
import com.qiaomu.modules.welfare.entity.TaskPublishUserEntity;
import com.qiaomu.modules.welfare.entity.TaskRecevieUserEntity;
import com.qiaomu.modules.welfare.model.PointRankForm;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wenglei on 2019/6/16.
 */

public interface PublicWelfareTaskService {
    String newTask(TaskEntity taskEntity);
    void newTaskUser(TaskPublishUserEntity taskUserEntity);
    void newPonit(PointEntity pointEntity);
    void updateTaskUser(TaskPublishUserEntity taskUserEntity);
    void updateTask(TaskEntity TaskEntity);
    void newTaskRecevieUser(TaskRecevieUserEntity taskRecevieUserEntity);
    void updateTaskRecevieUser(TaskRecevieUserEntity taskRecevieUserEntity);
    TaskEntity queryTask(String serviceId);
    TaskPublishUserEntity queryPublishUserTask(String serviceId);
    TaskRecevieUserEntity queryRecevieUserTask(String serviceId);
    TaskPublishUserEntity queryPublishUserTaskLast(String serviceId);
    List<TaskEntity> queryPublishUserServices(String communityId,String publishUserId);
    List<TaskEntity> queryRecevieUserServices(String receiveUserId);
    List<TaskEntity> queryAllunReviewTask(String receiveUserId);
    void updatePonit(PointEntity pointEntity);
    PointEntity selectPonitByUserId(String userId);
    List<PointRankForm> selectTopPointUser(String communityId);
    List<TaskEntity> queryAllTask(String communityId);
    List<TaskEntity> queryAllSubmitReviewTask(String receiveUserId);
    List<TaskEntity> queryAllRuningTask1(String userId);
    List<TaskEntity> queryAllRuningTask2(String userId);
    List<TaskEntity> queryAllCompleteTask(String userId);
}
