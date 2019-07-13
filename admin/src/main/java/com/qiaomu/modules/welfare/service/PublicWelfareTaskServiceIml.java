package com.qiaomu.modules.welfare.service;

import com.qiaomu.modules.welfare.dao.WelfareTaskDao;
import com.qiaomu.modules.welfare.entity.PointEntity;
import com.qiaomu.modules.welfare.entity.TaskEntity;
import com.qiaomu.modules.welfare.entity.TaskPublishUserEntity;
import com.qiaomu.modules.welfare.entity.TaskRecevieUserEntity;
import com.qiaomu.modules.welfare.model.PointRankForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void newPonit(PointEntity pointEntity) {
        welfareTaskDao.newPonit(pointEntity);
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

    @Override
    public List<TaskEntity> queryPublishUserServices(String publishUserId) {
        return welfareTaskDao.queryPublishUserServices(publishUserId);
    }

    @Override
    public List<TaskEntity> queryRecevieUserServices(String receiveUserId) {
        return welfareTaskDao.queryRecevieUserServices(receiveUserId);
    }

    @Override
    public List<TaskEntity> queryAllunReviewTask(String receiveUserId) {
        return welfareTaskDao.queryAllunReviewTask(receiveUserId);
    }

    @Override
    public void updatePonit(PointEntity pointEntity) {
        welfareTaskDao.updatePonit(pointEntity);
    }

    @Override
    public PointEntity selectPonitByUserId(String userId) {
        return welfareTaskDao.selectPonitByUserId(userId);
    }

    @Override
    public List<PointRankForm> selectTopPointUser() {
        return welfareTaskDao.selectTopPointUser();
    }

    @Override
    public List<TaskEntity> queryAllSubmitReviewTask(String receiveUserId) {
        return welfareTaskDao.queryAllSubmitReviewTask(receiveUserId);
    }

    @Override
    public List<TaskEntity> queryAllRuningTask1(String userId) {
        return welfareTaskDao.queryAllRuningTask1(userId);
    }

    @Override
    public List<TaskEntity> queryAllRuningTask2(String userId) {
        return welfareTaskDao.queryAllRuningTask2(userId);
    }

    @Override
    public List<TaskEntity> queryAllCompleteTask(String userId) {
        return welfareTaskDao.queryAllCompleteTask(userId);
    }
}
