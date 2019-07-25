package com.qiaomu.modules.welfare.bussiness;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.common.utils.CommonUtils;
import com.qiaomu.common.utils.DateUtils;
import com.qiaomu.common.validator.Assert;
import com.qiaomu.modules.welfare.entity.PointEntity;
import com.qiaomu.modules.welfare.entity.TaskEntity;
import com.qiaomu.modules.welfare.entity.TaskPublishUserEntity;
import com.qiaomu.modules.welfare.entity.TaskRecevieUserEntity;
import com.qiaomu.modules.welfare.exception.WelfareException;
import com.qiaomu.modules.welfare.model.PointRankForm;
import com.qiaomu.modules.welfare.model.TaskModel;
import com.qiaomu.modules.welfare.service.PublicWelfareTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.qiaomu.common.utils.Constant.OUT_DIR;
import static com.qiaomu.common.utils.Constant.SERVER_URL;
import static com.qiaomu.modules.welfare.constant.Status.*;

/**
 * Created by wenglei on 2019/7/13.
 */
@Service
public class WelfareBussiness {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PublicWelfareTaskService publicWelfareTaskService;


    public void publishTask(StandardMultipartHttpServletRequest req){
        String savePath =OUT_DIR+"image/";    //需要放到spring容器中，待修改;()

        Map<String,String[]> params = req.getParameterMap();
        String serviceName = CommonUtils.getMapValue("serviceName",params)[0];
        String seviceDetail = CommonUtils.getMapValue("seviceDetail",params)[0];
        String points = CommonUtils.getMapValue("points",params)[0];
        String publishUserId = CommonUtils.getMapValue("publishUserId",params)[0];
        String communityId = CommonUtils.getMapValue("communityId",params)[0];


        Map<String,String> imageUrls = new HashMap<String,String>();
        Iterator<String> filenames=req.getFileNames();


        while(filenames.hasNext()){
            String filekey = filenames.next();
            MultipartFile multipartFile = req.getFile(filekey);
            String fileName =CommonUtils.mkFileName(multipartFile.getOriginalFilename()) ;
            File saveFile = new File(savePath+fileName);
            try {
                multipartFile.transferTo(saveFile);
                imageUrls.put(filekey,SERVER_URL+"/outapp/image/"+fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        Date date = new Date();
        String createdTime = DateUtils.formats(date);
        PointEntity withpoints = publicWelfareTaskService.selectPonitByUserId(publishUserId);
        withpoints = checkPint(withpoints,publishUserId);
        Integer point = 0;
        try {
            point = Integer.valueOf(points);
        }catch (Exception e){
            throw new WelfareException("任务金币只能是整数");
        }
        if(Integer.valueOf(withpoints.getPoints())<point){
            throw new WelfareException("金币不足，不能创建！");
        }else if(point>10){
            throw new WelfareException("单次任务金币不能超过10！");
        }


        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setPoints(points);
        taskEntity.setCommunityId(communityId);
        taskEntity.setServiceName(serviceName);
        taskEntity.setSeviceDetail(seviceDetail);
        taskEntity.setStatus(UNGET.value);
        taskEntity.setCreatedAt(createdTime);
        taskEntity.setUpdatedAt(createdTime);
        taskEntity.setImageUrls(JSON.toJSONString(imageUrls));
        String serviceId = publicWelfareTaskService.newTask(taskEntity);
        TaskPublishUserEntity taskUserEntity = new TaskPublishUserEntity();
        taskUserEntity.setPublishUserId(publishUserId);
        taskUserEntity.setServiceId(serviceId);
        taskUserEntity.setStatus(UNRUN.value);
        taskUserEntity.setCreatedAt(createdTime);
        taskUserEntity.setUpdatedAt(createdTime);
        publicWelfareTaskService.newTaskUser(taskUserEntity);
    }

    public void getTask(TaskModel taskModel){
        Date date = new Date();
        String updatedTime = DateUtils.formats(date);

        TaskEntity taskEntity = new TaskEntity();
        TaskPublishUserEntity taskPublishUserEntity = publicWelfareTaskService.queryPublishUserTaskLast(taskModel.getServiceId());
        if(taskPublishUserEntity!=null&&!UNRUN.value.equals(taskPublishUserEntity.getStatus())){
            throw new WelfareException("任务不可领取");
        }
        TaskRecevieUserEntity taskRecevieUserEntity = new TaskRecevieUserEntity();
        taskEntity.setStatus(RUN.value);
        taskEntity.setServiceId(taskModel.getServiceId());
        taskEntity.setUpdatedAt(updatedTime);

        taskPublishUserEntity.setStatus(RUN.value);
        taskPublishUserEntity.setServiceId(taskModel.getServiceId());
        taskPublishUserEntity.setCreatedAt(updatedTime);
        taskPublishUserEntity.setUpdatedAt(updatedTime);

        taskRecevieUserEntity.setStatus(RUN.value);
        taskRecevieUserEntity.setServiceId(taskModel.getServiceId());
        taskRecevieUserEntity.setReceiveUserId(taskModel.getReceiveUserId());
        taskRecevieUserEntity.setCreatedAt(updatedTime);
        taskRecevieUserEntity.setUpdatedAt(updatedTime);


        publicWelfareTaskService.updateTask(taskEntity);
        publicWelfareTaskService.newTaskUser(taskPublishUserEntity);
        publicWelfareTaskService.newTaskRecevieUser(taskRecevieUserEntity);
    }

    public void cancelGetTask(TaskModel taskModel){
        Date date = new Date();
        String updatedTime = DateUtils.formats(date);
        TaskEntity taskEntity = new TaskEntity();
        TaskPublishUserEntity taskPublishUserEntity = publicWelfareTaskService.queryPublishUserTaskLast(taskModel.getServiceId());
        TaskRecevieUserEntity taskRecevieUserEntity = publicWelfareTaskService.queryRecevieUserTask(taskModel.getServiceId());
        Assert.isNull(taskPublishUserEntity,"未找到该任务");
        Assert.isNull(taskRecevieUserEntity,"未领取该任务");
        if(!RUN.value.equals(taskRecevieUserEntity.getStatus())){
            throw new WelfareException("当前任务不可取消");
        }
        taskEntity.setStatus(UNGET.value);
        taskEntity.setServiceId(taskModel.getServiceId());
        taskEntity.setUpdatedAt(updatedTime);

        taskPublishUserEntity.setStatus(UNRUN.value);
        taskPublishUserEntity.setServiceId(taskModel.getServiceId());
        taskPublishUserEntity.setCreatedAt(updatedTime);
        taskPublishUserEntity.setUpdatedAt(updatedTime);

        taskRecevieUserEntity.setStatus(CANCEL.value);
        taskRecevieUserEntity.setServiceId(taskModel.getServiceId());
        taskRecevieUserEntity.setReceiveUserId(taskModel.getReceiveUserId());
        taskRecevieUserEntity.setCreatedAt(updatedTime);
        taskRecevieUserEntity.setUpdatedAt(updatedTime);

        publicWelfareTaskService.updateTask(taskEntity);
        publicWelfareTaskService.newTaskUser(taskPublishUserEntity);
        publicWelfareTaskService.newTaskRecevieUser(taskRecevieUserEntity);

    }

    public void cancelPublishTask(TaskModel taskModel){
        Date date = new Date();
        String updatedTime = DateUtils.formats(date);
        TaskEntity taskEntity = publicWelfareTaskService.queryTask(taskModel.getServiceId());

        if(UNGET.value.equals(taskEntity.getStatus())){
            taskEntity.setStatus(CANCEL.value);
            taskEntity.setUpdatedAt(updatedTime);
            TaskPublishUserEntity taskPublishUserEntity = publicWelfareTaskService.queryPublishUserTask(taskModel.getServiceId());
            taskPublishUserEntity.setStatus(CANCEL.value);
            taskPublishUserEntity.setServiceId(taskModel.getServiceId());
            taskPublishUserEntity.setCreatedAt(updatedTime);
            taskPublishUserEntity.setUpdatedAt(updatedTime);
            publicWelfareTaskService.updateTask(taskEntity);//更新状态
            publicWelfareTaskService.newTaskUser(taskPublishUserEntity);
        }else{
            throw new WelfareException("不可取消已领取任务");
        }

    }

    public TaskRecevieUserEntity submitReview(TaskModel taskModel){

        Date date = new Date();
        String updatedTime = DateUtils.formats(date);

        TaskPublishUserEntity taskPublishUserEntity = publicWelfareTaskService.queryPublishUserTaskLast(taskModel.getServiceId());
        TaskRecevieUserEntity taskRecevieUserEntity = publicWelfareTaskService.queryRecevieUserTask(taskModel.getServiceId());
        Assert.isNull(taskPublishUserEntity,"未找到该任务");
        Assert.isNull(taskRecevieUserEntity,"未领取该任务");
        if(!RUN.value.equals(taskRecevieUserEntity.getStatus())){
            throw new WelfareException("当前任务不可审核");
        }

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setStatus(REVIEWED.value);
        taskEntity.setUpdatedAt(updatedTime);
        taskEntity.setServiceId(taskModel.getServiceId());

        taskPublishUserEntity.setStatus(REVIEWING.value);
        taskPublishUserEntity.setServiceId(taskModel.getServiceId());
        taskPublishUserEntity.setCreatedAt(updatedTime);
        taskPublishUserEntity.setUpdatedAt(updatedTime);

        taskRecevieUserEntity.setStatus(REVIEWED.value);
        taskRecevieUserEntity.setServiceId(taskModel.getServiceId());
        taskRecevieUserEntity.setReceiveUserId(taskModel.getReceiveUserId());
        taskRecevieUserEntity.setCreatedAt(updatedTime);
        taskRecevieUserEntity.setUpdatedAt(updatedTime);

        publicWelfareTaskService.updateTask(taskEntity);//更新状态
        publicWelfareTaskService.newTaskUser(taskPublishUserEntity);
        publicWelfareTaskService.newTaskRecevieUser(taskRecevieUserEntity);
        return taskRecevieUserEntity;

    }

    public TaskPublishUserEntity reviewSuccess(TaskModel taskModel){

        Date date = new Date();
        String updatedTime = DateUtils.formats(date);

        TaskPublishUserEntity taskPublishUserEntity = publicWelfareTaskService.queryPublishUserTaskLast(taskModel.getServiceId());
        TaskRecevieUserEntity taskRecevieUserEntity = publicWelfareTaskService.queryRecevieUserTask(taskModel.getServiceId());
        Assert.isNull(taskPublishUserEntity,"未找到该任务");
        Assert.isNull(taskRecevieUserEntity,"未领取该任务");
        if(!REVIEWED.value.equals(taskRecevieUserEntity.getStatus())){
            throw new WelfareException("当前任务不可审核");
        }


        taskPublishUserEntity.setStatus(COMPLETE.value);
        taskPublishUserEntity.setServiceId(taskModel.getServiceId());
        taskPublishUserEntity.setCreatedAt(updatedTime);
        taskPublishUserEntity.setUpdatedAt(updatedTime);

        taskRecevieUserEntity.setStatus(COMPLETE.value);
        taskRecevieUserEntity.setServiceId(taskModel.getServiceId());
        taskRecevieUserEntity.setReceiveUserId(taskRecevieUserEntity.getReceiveUserId());
        taskRecevieUserEntity.setCreatedAt(updatedTime);
        taskRecevieUserEntity.setUpdatedAt(updatedTime);

        publicWelfareTaskService.newTaskUser(taskPublishUserEntity);
        publicWelfareTaskService.newTaskRecevieUser(taskRecevieUserEntity);
        PointEntity points = publicWelfareTaskService.selectPonitByUserId(taskRecevieUserEntity.getReceiveUserId());
        PointEntity publisherPoints = publicWelfareTaskService.selectPonitByUserId(taskPublishUserEntity.getPublishUserId());
        //新用户初始金币100
        points = checkPint(points,taskRecevieUserEntity.getReceiveUserId());
        publisherPoints = checkPint(publisherPoints,taskPublishUserEntity.getPublishUserId());
        //增加任务金币
        TaskEntity taskEntity = publicWelfareTaskService.queryTask(taskModel.getServiceId());
        String taskPoint = taskEntity.getPoints();
        if(taskPoint!=null&&!taskPoint.contains(".")){
            points.setPoints(String.valueOf(Integer.valueOf(taskPoint)+Integer.valueOf(points.getPoints())));
            publisherPoints.setPoints(String.valueOf(Integer.valueOf(publisherPoints.getPoints())-Integer.valueOf(taskPoint)));
            publicWelfareTaskService.updatePonit(points);
            publicWelfareTaskService.updatePonit(publisherPoints);
        }
        taskEntity.setStatus(COMPLETE.value);
        taskEntity.setUpdatedAt(updatedTime);
        publicWelfareTaskService.updateTask(taskEntity);//更新状态
        return taskPublishUserEntity;

    }

    private PointEntity checkPint(PointEntity points,String userId){
        Date date = new Date();
        String updatedTime = DateUtils.formats(date);
        if(points==null){
            points = new PointEntity();
            String createdTime = DateUtils.formats(date);
            points.setPoints(String.valueOf(100));
            points.setUserId(userId);
            points.setCreatedAt(updatedTime);
            points.setUpdatedAt(updatedTime);
            publicWelfareTaskService.newPonit(points);
        }
        return points;
    }

    public TaskPublishUserEntity reviewFail(TaskModel taskModel){

        Date date = new Date();
        String updatedTime = DateUtils.formats(date);

        TaskPublishUserEntity taskPublishUserEntity = publicWelfareTaskService.queryPublishUserTaskLast(taskModel.getServiceId());
        TaskRecevieUserEntity taskRecevieUserEntity = publicWelfareTaskService.queryRecevieUserTask(taskModel.getServiceId());
        Assert.isNull(taskPublishUserEntity,"未找到该任务");
        Assert.isNull(taskRecevieUserEntity,"未领取该任务");
        if(!REVIEWED.value.equals(taskRecevieUserEntity.getStatus())){
            throw new WelfareException("当前任务不可审核");
        }


        taskPublishUserEntity.setStatus(RUN.value);
        taskPublishUserEntity.setServiceId(taskModel.getServiceId());
        taskPublishUserEntity.setCreatedAt(updatedTime);
        taskPublishUserEntity.setUpdatedAt(updatedTime);

        taskRecevieUserEntity.setStatus(RUN.value);
        taskRecevieUserEntity.setServiceId(taskModel.getServiceId());
        taskRecevieUserEntity.setCreatedAt(updatedTime);
        taskRecevieUserEntity.setUpdatedAt(updatedTime);
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setStatus(RUN.value);
        taskEntity.setUpdatedAt(updatedTime);
        publicWelfareTaskService.updateTask(taskEntity);//更新状态
        publicWelfareTaskService.newTaskUser(taskPublishUserEntity);
        publicWelfareTaskService.newTaskRecevieUser(taskRecevieUserEntity);
        return taskPublishUserEntity;

    }

    public Map<String,Object> statisticGold(String userId){
        PointEntity points = publicWelfareTaskService.selectPonitByUserId(userId);
        //新用户初始金币100
        points = checkPint(points,userId);
        Map<String,Object> returnMap = BuildResponse.success();
        returnMap.put("points",points);
        return returnMap;

    }

    public List<TaskEntity> queryAllSubmitReviewTask(String receiveUserId){
        List<TaskEntity> tasks = publicWelfareTaskService.queryAllSubmitReviewTask(receiveUserId);
        return tasks;

    }

    public Map<String,Object> queryAllRuningTask(String userId){
        List<TaskEntity>  publishTasks = publicWelfareTaskService.queryAllRuningTask1(userId);
        List<TaskEntity>  receiveTasks = publicWelfareTaskService.queryAllRuningTask2(userId);
        Map<String,Object> tasks = new HashMap<>();
        tasks.put("publishTasks",publishTasks);
        tasks.put("receiveTasks",receiveTasks);
        return tasks;
    }

    public List<TaskEntity> queryAllCompleteTask(String userId){
        List<TaskEntity>  tasks = publicWelfareTaskService.queryAllCompleteTask(userId);
        return tasks;
    }

    public List<TaskEntity> queryAllunReviewTask(String publishUserId){
        List<TaskEntity>  tasks = publicWelfareTaskService.queryAllunReviewTask(publishUserId);
        return tasks;
    }

    public List<TaskEntity> queryAllCreatedTask(String publishUserId){
        List<TaskEntity>  tasks = publicWelfareTaskService.queryPublishUserServices(publishUserId);
        return tasks;
    }

    public List<TaskEntity> queryAllGetTask(String receiveUserId){
        List<TaskEntity>  tasks = publicWelfareTaskService.queryRecevieUserServices(receiveUserId);
        return tasks;
    }

    public List<PointRankForm> rankByGold(String communityId){
        List<PointRankForm> result = publicWelfareTaskService.selectTopPointUser(communityId);
        return result;

    }

    public List<TaskEntity> queryAllTask(String communityId){
        return publicWelfareTaskService.queryAllTask(communityId);
    }


}
