package com.qiaomu.modules.welfare.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.infopublish_publish.entity.InvitationEntity;
import com.qiaomu.modules.welfare.entity.PointEntity;
import com.qiaomu.modules.welfare.entity.TaskEntity;
import com.qiaomu.modules.welfare.entity.TaskPublishUserEntity;
import com.qiaomu.modules.welfare.entity.TaskRecevieUserEntity;

import java.util.List;

/**
 * Created by wenglei on 2019/6/20.
 */
public interface WelfareTaskDao extends BaseMapper<InvitationEntity> {
    void newTask(TaskEntity taskEntity);
    void newTaskUser(TaskPublishUserEntity taskUserEntity);
    void newTaskRecevieUser(TaskRecevieUserEntity taskRecevieUserEntity);
    void newPonit(PointEntity pointEntity);

    TaskEntity queryTask(String serviceId);
    TaskEntity queryTaskByName(String name);
    TaskPublishUserEntity queryPublishUserTask(String serviceId);
    TaskRecevieUserEntity queryRecevieUserTask(String serviceId);
    TaskPublishUserEntity queryPublishUserTaskLast(String serviceId);
    List<TaskEntity> queryPublishUserServices(String publishUserId);
    List<TaskEntity> queryRecevieUserServices(String receiveUserId);
    List<TaskEntity> queryAllunReviewTask(String receiveUserId);
    PointEntity selectPonitByUserId(String userId);
    List<PointEntity> selectTopPointUser();

    void updateTask(TaskEntity TaskEntity);
    void updateTaskUser(TaskPublishUserEntity taskUserEntity);
    void updateTaskRecevieUser(TaskRecevieUserEntity taskRecevieUserEntity);
    void updatePonit(PointEntity pointEntity);

}
