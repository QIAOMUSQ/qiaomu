package com.qiaomu.modules.welfare.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.infopublish_publish.entity.InvitationEntity;
import com.qiaomu.modules.welfare.entity.TaskEntity;
import com.qiaomu.modules.welfare.entity.TaskPublishUserEntity;
import com.qiaomu.modules.welfare.entity.TaskRecevieUserEntity;

/**
 * Created by wenglei on 2019/6/20.
 */
public interface WelfareTaskDao extends BaseMapper<InvitationEntity> {
    void newTask(TaskEntity taskEntity);
    void newTaskUser(TaskPublishUserEntity taskUserEntity);
    void newTaskRecevieUser(TaskRecevieUserEntity taskRecevieUserEntity);

    TaskEntity queryTask(String serviceId);
    TaskEntity queryTaskByName(String name);
    TaskPublishUserEntity queryPublishUserTask(String serviceId);
    TaskRecevieUserEntity queryRecevieUserTask(String serviceId);
    TaskPublishUserEntity queryPublishUserTaskLast(String serviceId);

    void updateTask(TaskEntity TaskEntity);
    void updateTaskUser(TaskPublishUserEntity taskUserEntity);
    void updateTaskRecevieUser(TaskRecevieUserEntity taskRecevieUserEntity);

}
