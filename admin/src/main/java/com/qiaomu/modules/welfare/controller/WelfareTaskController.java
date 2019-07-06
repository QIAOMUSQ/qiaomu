package com.qiaomu.modules.welfare.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.BuildResponse;

import com.qiaomu.common.utils.DateUtils;
import com.qiaomu.common.validator.Assert;
import com.qiaomu.modules.welfare.entity.PointEntity;
import com.qiaomu.modules.welfare.entity.TaskEntity;
import com.qiaomu.modules.welfare.entity.TaskPublishUserEntity;
import com.qiaomu.modules.welfare.entity.TaskRecevieUserEntity;
import com.qiaomu.modules.welfare.model.TaskModel;
import com.qiaomu.modules.welfare.service.PublicWelfareTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wenglei on 2019/6/15.
 */
@RestController
@RequestMapping(value = "welfare")
public class WelfareTaskController {
    @Autowired
    private PublicWelfareTaskService publicWelfareTaskService;

    /**
     * 发布任务
     * @param
     * @return
     */
    @RequestMapping(value = "publishTask",method = RequestMethod.POST)
    public String publishTask(TaskModel taskModel){
        Date date = new Date();
        String createdTime = DateUtils.formats(date);

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setPoints(taskModel.getPoints());
        taskEntity.setServiceName(taskModel.getServiceName());
        taskEntity.setSeviceDetail(taskModel.getSeviceDetail());
        taskEntity.setStatus("未领取");
        taskEntity.setCreatedAt(createdTime);
        taskEntity.setUpdatedAt(createdTime);
        String serviceId = publicWelfareTaskService.newTask(taskEntity);
        TaskPublishUserEntity taskUserEntity = new TaskPublishUserEntity();
        taskUserEntity.setPublishUserId(taskModel.getPublishUserId());
        taskUserEntity.setServiceId(serviceId);
        taskUserEntity.setStatus("未执行");
        taskUserEntity.setCreatedAt(createdTime);
        taskUserEntity.setUpdatedAt(createdTime);
        publicWelfareTaskService.newTaskUser(taskUserEntity);
        return JSON.toJSONString(BuildResponse.success());

    }


    /**
     * 查询所有待审核的任务
     * @param
     * @return
     */
    @RequestMapping(value = "queryAllunReviewTask",method = RequestMethod.POST)
    public String queryAllunReviewTask(String publishUserId){
        List<TaskEntity>  tasks = publicWelfareTaskService.queryAllunReviewTask(publishUserId);
        return JSON.toJSONString(BuildResponse.success(tasks));

    }

    /**
     * 查询用户创建的所有任务
     * @param
     * @return
     */
    @RequestMapping(value = "queryAllCreatedTask",method = RequestMethod.POST)
    public String queryAllCreatedTask(String publishUserId){
        List<TaskEntity>  tasks = publicWelfareTaskService.queryPublishUserServices(publishUserId);
        return JSON.toJSONString(BuildResponse.success(tasks));

    }

    /**
     * 查询用户领取的所有任务
     * @param
     * @return
     */
    @RequestMapping(value = "queryAllGetTask",method = RequestMethod.POST)
    public String queryAllGetTask(String receiveUserId){
        List<TaskEntity>  tasks = publicWelfareTaskService.queryRecevieUserServices(receiveUserId);
        return JSON.toJSONString(BuildResponse.success(tasks));

    }

    /**
     * 领取任务
     * @param
     * @return
     */
    @RequestMapping(value = "getTask",method = RequestMethod.POST)
    public String getTask(TaskModel taskModel){
        Date date = new Date();
        String updatedTime = DateUtils.formats(date);

        TaskEntity taskEntity = new TaskEntity();
        TaskPublishUserEntity taskPublishUserEntity = publicWelfareTaskService.queryPublishUserTaskLast(taskModel.getServiceId());
        if(taskPublishUserEntity!=null&&!"未执行".equals(taskPublishUserEntity.getStatus())){
            return JSON.toJSONString(BuildResponse.fail("2002","任务不可领取"));
        }
        TaskRecevieUserEntity taskRecevieUserEntity = new TaskRecevieUserEntity();
        taskEntity.setStatus("已领取");
        taskEntity.setServiceId(taskModel.getServiceId());
        taskEntity.setUpdatedAt(updatedTime);

        taskPublishUserEntity.setStatus("执行中");
        taskPublishUserEntity.setServiceId(taskModel.getServiceId());
        taskPublishUserEntity.setCreatedAt(updatedTime);
        taskPublishUserEntity.setUpdatedAt(updatedTime);

        taskRecevieUserEntity.setStatus("执行中");
        taskRecevieUserEntity.setServiceId(taskModel.getServiceId());
        taskRecevieUserEntity.setReceiveUserId(taskModel.getReceiveUserId());
        taskRecevieUserEntity.setCreatedAt(updatedTime);
        taskRecevieUserEntity.setUpdatedAt(updatedTime);


        publicWelfareTaskService.updateTask(taskEntity);
        publicWelfareTaskService.newTaskUser(taskPublishUserEntity);
        publicWelfareTaskService.newTaskRecevieUser(taskRecevieUserEntity);
        return JSON.toJSONString(BuildResponse.success());

    }

    /**
     * 取消领取任务
     * @param
     * @return
     */
    @RequestMapping(value = "cancelGetTask",method = RequestMethod.POST)
    public String cancelGetTask(TaskModel taskModel){
        Date date = new Date();
        String updatedTime = DateUtils.formats(date);
        TaskEntity taskEntity = new TaskEntity();
        TaskPublishUserEntity taskPublishUserEntity = publicWelfareTaskService.queryPublishUserTaskLast(taskModel.getServiceId());
        TaskRecevieUserEntity taskRecevieUserEntity = publicWelfareTaskService.queryRecevieUserTask(taskModel.getServiceId());
        Assert.isNull(taskPublishUserEntity,"未找到该任务");
        Assert.isNull(taskRecevieUserEntity,"未领取该任务");
        if(!"执行中".equals(taskRecevieUserEntity.getStatus())){
            return JSON.toJSONString(BuildResponse.fail("2003","当前任务不可取消"));
        }
        taskEntity.setStatus("未领取");
        taskEntity.setServiceId(taskModel.getServiceId());
        taskEntity.setUpdatedAt(updatedTime);

        taskPublishUserEntity.setStatus("未执行");
        taskPublishUserEntity.setServiceId(taskModel.getServiceId());
        taskPublishUserEntity.setCreatedAt(updatedTime);
        taskPublishUserEntity.setUpdatedAt(updatedTime);

        taskRecevieUserEntity.setStatus("已取消");
        taskRecevieUserEntity.setServiceId(taskModel.getServiceId());
        taskRecevieUserEntity.setReceiveUserId(taskModel.getReceiveUserId());
        taskRecevieUserEntity.setCreatedAt(updatedTime);
        taskRecevieUserEntity.setUpdatedAt(updatedTime);

        publicWelfareTaskService.updateTask(taskEntity);
        publicWelfareTaskService.newTaskUser(taskPublishUserEntity);
        publicWelfareTaskService.newTaskRecevieUser(taskRecevieUserEntity);
        return JSON.toJSONString(BuildResponse.success());

    }

    /**
     * 取消发布的任务
     * @param
     * @return
     */
    @RequestMapping(value = "cancelPublishTask",method = RequestMethod.POST)
    public String cancelPublishTask(TaskModel taskModel){
        Date date = new Date();
        String updatedTime = DateUtils.formats(date);
        TaskEntity taskEntity = publicWelfareTaskService.queryTask(taskModel.getServiceId());

        if("未领取".equals(taskEntity.getStatus())){
            taskEntity.setStatus("已取消");
            taskEntity.setUpdatedAt(updatedTime);
            TaskPublishUserEntity taskPublishUserEntity = publicWelfareTaskService.queryPublishUserTask(taskModel.getServiceId());
            taskPublishUserEntity.setStatus("已取消");
            taskPublishUserEntity.setServiceId(taskModel.getServiceId());
            taskPublishUserEntity.setCreatedAt(updatedTime);
            taskPublishUserEntity.setUpdatedAt(updatedTime);
            publicWelfareTaskService.updateTask(taskEntity);//更新状态
            publicWelfareTaskService.updateTaskUser(taskPublishUserEntity);
        }else{
            return JSON.toJSONString(BuildResponse.fail("2001","不可取消已领取任务"));
        }
        return JSON.toJSONString(BuildResponse.success());

    }

    /**
     * 提交审核
     * @param
     * @return
     */
    @RequestMapping(value = "submitReview",method = RequestMethod.POST)
    public String submitReview(TaskModel taskModel){

        //待审核
        //审核中
        Date date = new Date();
        String updatedTime = DateUtils.formats(date);

        TaskPublishUserEntity taskPublishUserEntity = publicWelfareTaskService.queryPublishUserTaskLast(taskModel.getServiceId());
        TaskRecevieUserEntity taskRecevieUserEntity = publicWelfareTaskService.queryRecevieUserTask(taskModel.getServiceId());
        Assert.isNull(taskPublishUserEntity,"未找到该任务");
        Assert.isNull(taskRecevieUserEntity,"未领取该任务");
        if(!"执行中".equals(taskRecevieUserEntity.getStatus())){
            return JSON.toJSONString(BuildResponse.fail("2004","当前任务不可审核"));
        }


        taskPublishUserEntity.setStatus("待审核");
        taskPublishUserEntity.setServiceId(taskModel.getServiceId());
        taskPublishUserEntity.setCreatedAt(updatedTime);
        taskPublishUserEntity.setUpdatedAt(updatedTime);

        taskRecevieUserEntity.setStatus("审核中");
        taskRecevieUserEntity.setServiceId(taskModel.getServiceId());
        taskRecevieUserEntity.setReceiveUserId(taskModel.getReceiveUserId());
        taskRecevieUserEntity.setCreatedAt(updatedTime);
        taskRecevieUserEntity.setUpdatedAt(updatedTime);

        publicWelfareTaskService.newTaskUser(taskPublishUserEntity);
        publicWelfareTaskService.newTaskRecevieUser(taskRecevieUserEntity);
        return JSON.toJSONString(BuildResponse.success(taskRecevieUserEntity));

    }
    /**
     * 审核通过
     * @param
     * @return
     */
    @RequestMapping(value = "reviewSuccess",method = RequestMethod.POST)
    public String reviewSuccess(TaskModel taskModel){

        Date date = new Date();
        String updatedTime = DateUtils.formats(date);

        TaskPublishUserEntity taskPublishUserEntity = publicWelfareTaskService.queryPublishUserTaskLast(taskModel.getServiceId());
        TaskRecevieUserEntity taskRecevieUserEntity = publicWelfareTaskService.queryRecevieUserTask(taskModel.getServiceId());
        Assert.isNull(taskPublishUserEntity,"未找到该任务");
        Assert.isNull(taskRecevieUserEntity,"未领取该任务");
        if(!"待审核".equals(taskRecevieUserEntity.getStatus())){
            return JSON.toJSONString(BuildResponse.fail("2005","当前任务不可审核"));
        }


        taskPublishUserEntity.setStatus("已完成");
        taskPublishUserEntity.setServiceId(taskModel.getServiceId());
        taskPublishUserEntity.setCreatedAt(updatedTime);
        taskPublishUserEntity.setUpdatedAt(updatedTime);

        taskRecevieUserEntity.setStatus("已完成");
        taskRecevieUserEntity.setServiceId(taskModel.getServiceId());
        taskRecevieUserEntity.setReceiveUserId(taskModel.getReceiveUserId());
        taskRecevieUserEntity.setCreatedAt(updatedTime);
        taskRecevieUserEntity.setUpdatedAt(updatedTime);

        publicWelfareTaskService.newTaskUser(taskPublishUserEntity);
        publicWelfareTaskService.newTaskRecevieUser(taskRecevieUserEntity);
        return JSON.toJSONString(BuildResponse.success(taskPublishUserEntity));

    }

    /**
     * 审核不通过
     * @param
     * @return
     */
    @RequestMapping(value = "reviewFail",method = RequestMethod.POST)
    public String reviewFail(TaskModel taskModel){

        Date date = new Date();
        String updatedTime = DateUtils.formats(date);

        TaskPublishUserEntity taskPublishUserEntity = publicWelfareTaskService.queryPublishUserTaskLast(taskModel.getServiceId());
        TaskRecevieUserEntity taskRecevieUserEntity = publicWelfareTaskService.queryRecevieUserTask(taskModel.getServiceId());
        Assert.isNull(taskPublishUserEntity,"未找到该任务");
        Assert.isNull(taskRecevieUserEntity,"未领取该任务");
        if(!"待审核".equals(taskRecevieUserEntity.getStatus())){
            return JSON.toJSONString(BuildResponse.fail("2005","当前任务不可审核"));
        }


        taskPublishUserEntity.setStatus("执行中");
        taskPublishUserEntity.setServiceId(taskModel.getServiceId());
        taskPublishUserEntity.setCreatedAt(updatedTime);
        taskPublishUserEntity.setUpdatedAt(updatedTime);

        taskRecevieUserEntity.setStatus("执行中");
        taskRecevieUserEntity.setServiceId(taskModel.getServiceId());
        taskRecevieUserEntity.setReceiveUserId(taskModel.getReceiveUserId());
        taskRecevieUserEntity.setCreatedAt(updatedTime);
        taskRecevieUserEntity.setUpdatedAt(updatedTime);

        publicWelfareTaskService.newTaskUser(taskPublishUserEntity);
        publicWelfareTaskService.newTaskRecevieUser(taskRecevieUserEntity);
        return JSON.toJSONString(BuildResponse.success(taskPublishUserEntity));

    }

    /**
     * 统计个人金币
     * @param
     * @return
     */
    @RequestMapping(value = "statisticGold",method = RequestMethod.POST)
    public String statisticGold(String userId){
        Integer points = publicWelfareTaskService.selectPonitByUserId(userId);

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("points",points);
        return JSON.toJSONString(BuildResponse.success(returnMap));

    }

    /**
     * 按照金币进行排行 TODO 用户信息
     * @param
     * @return
     */
    @RequestMapping(value = "rankByGold",method = RequestMethod.POST)
    public String rankByGold(){
        publicWelfareTaskService.selectTopPointUser();
        return JSON.toJSONString(BuildResponse.success());

    }
}
