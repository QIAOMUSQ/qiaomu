package com.qiaomu.modules.welfare.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.BuildResponse;

import com.qiaomu.common.utils.CommonUtils;
import com.qiaomu.common.utils.DateUtils;
import com.qiaomu.common.validator.Assert;
import com.qiaomu.modules.welfare.entity.PointEntity;
import com.qiaomu.modules.welfare.entity.TaskEntity;
import com.qiaomu.modules.welfare.entity.TaskPublishUserEntity;
import com.qiaomu.modules.welfare.entity.TaskRecevieUserEntity;
import com.qiaomu.modules.welfare.model.PointRankForm;
import com.qiaomu.modules.welfare.model.TaskModel;
import com.qiaomu.modules.welfare.service.PublicWelfareTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.qiaomu.common.utils.Constant.OUT_DIR;
import static com.qiaomu.common.utils.Constant.SERVER_URL;

/**
 * Created by wenglei on 2019/6/15.
 */
@RestController
@RequestMapping(value = "mobile/welfare")
public class WelfareTaskController {
    @Autowired
    private PublicWelfareTaskService publicWelfareTaskService;

    /**
     * 发布任务
     * @param
     * @return
     */
    @RequestMapping(value = "publishTask",method = RequestMethod.POST)
    public String publishTask(HttpServletRequest request){

        String savePath =OUT_DIR+"image/";    //需要放到spring容器中，待修改;()

        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;

        Map<String,String[]> params = req.getParameterMap();
        String serviceName = CommonUtils.getMapValue("serviceName",params)[0];
        String seviceDetail = CommonUtils.getMapValue("seviceDetail",params)[0];
        String points = CommonUtils.getMapValue("points",params)[0];
        String publishUserId = CommonUtils.getMapValue("publishUserId",params)[0];

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
        try {
            Integer point = Integer.valueOf(points);
            if(Integer.valueOf(withpoints.getPoints())<point){
                return JSON.toJSONString(BuildResponse.fail("积分不足，不能创建！"));
            }
        }catch (Exception e){
            return JSON.toJSONString(BuildResponse.fail("任务积分只能是整数"));
        }

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setPoints(points);
        taskEntity.setServiceName(serviceName);
        taskEntity.setSeviceDetail(seviceDetail);
        taskEntity.setStatus("未领取");
        taskEntity.setCreatedAt(createdTime);
        taskEntity.setUpdatedAt(createdTime);
        taskEntity.setImageUrls(JSON.toJSONString(imageUrls));
        String serviceId = publicWelfareTaskService.newTask(taskEntity);
        TaskPublishUserEntity taskUserEntity = new TaskPublishUserEntity();
        taskUserEntity.setPublishUserId(publishUserId);
        taskUserEntity.setServiceId(serviceId);
        taskUserEntity.setStatus("未执行");
        taskUserEntity.setCreatedAt(createdTime);
        taskUserEntity.setUpdatedAt(createdTime);
        publicWelfareTaskService.newTaskUser(taskUserEntity);
        return JSON.toJSONString(BuildResponse.success());

    }

    /**
     * 查询所有审核中的任务
     * @param
     * @return
     */
    @RequestMapping(value = "queryAllSubmitReviewTask",method = RequestMethod.POST)
    public String queryAllSubmitReviewTask(String receiveUserId){
        List<TaskEntity>  tasks = publicWelfareTaskService.queryAllSubmitReviewTask(receiveUserId);
        return JSON.toJSONString(BuildResponse.success(tasks));

    }

    /**
     * 查询所有执行中的任务
     * @param
     * @return
     */
    @RequestMapping(value = "queryAllRuningTask",method = RequestMethod.POST)
    public String queryAllRuningTask(String userId){
        List<TaskEntity>  publishTasks = publicWelfareTaskService.queryAllRuningTask1(userId);
        List<TaskEntity>  receiveTasks = publicWelfareTaskService.queryAllRuningTask2(userId);
        Map<String,Object> tasks = new HashMap<>();
        tasks.put("publishTasks",publishTasks);
        tasks.put("receiveTasks",receiveTasks);
        return JSON.toJSONString(BuildResponse.success(tasks));

    }

    /**
     * 查询所有已完成的任务
     * @param
     * @return
     */
    @RequestMapping(value = "queryAllCompleteTask",method = RequestMethod.POST)
    public String queryAllCompleteTask(String userId){
        List<TaskEntity>  tasks = publicWelfareTaskService.queryAllCompleteTask(userId);
        return JSON.toJSONString(BuildResponse.success(tasks));

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
            return JSON.toJSONString(BuildResponse.fail("任务不可领取"));
        }
        TaskRecevieUserEntity taskRecevieUserEntity = new TaskRecevieUserEntity();
        taskEntity.setStatus("执行中");
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
            return JSON.toJSONString(BuildResponse.fail("当前任务不可取消"));
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
            publicWelfareTaskService.newTaskUser(taskPublishUserEntity);
        }else{
            return JSON.toJSONString(BuildResponse.fail("不可取消已领取任务"));
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
            return JSON.toJSONString(BuildResponse.fail("当前任务不可审核"));
        }

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setStatus("审核中");
        taskEntity.setUpdatedAt(updatedTime);

        taskPublishUserEntity.setStatus("待审核");
        taskPublishUserEntity.setServiceId(taskModel.getServiceId());
        taskPublishUserEntity.setCreatedAt(updatedTime);
        taskPublishUserEntity.setUpdatedAt(updatedTime);

        taskRecevieUserEntity.setStatus("审核中");
        taskRecevieUserEntity.setServiceId(taskModel.getServiceId());
        taskRecevieUserEntity.setReceiveUserId(taskModel.getReceiveUserId());
        taskRecevieUserEntity.setCreatedAt(updatedTime);
        taskRecevieUserEntity.setUpdatedAt(updatedTime);

        publicWelfareTaskService.updateTask(taskEntity);//更新状态
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
        if(!"审核中".equals(taskRecevieUserEntity.getStatus())){
            return JSON.toJSONString(BuildResponse.fail("当前任务不可审核"));
        }


        taskPublishUserEntity.setStatus("已完成");
        taskPublishUserEntity.setServiceId(taskModel.getServiceId());
        taskPublishUserEntity.setCreatedAt(updatedTime);
        taskPublishUserEntity.setUpdatedAt(updatedTime);

        taskRecevieUserEntity.setStatus("已完成");
        taskRecevieUserEntity.setServiceId(taskModel.getServiceId());
        taskRecevieUserEntity.setReceiveUserId(taskRecevieUserEntity.getReceiveUserId());
        taskRecevieUserEntity.setCreatedAt(updatedTime);
        taskRecevieUserEntity.setUpdatedAt(updatedTime);

        publicWelfareTaskService.newTaskUser(taskPublishUserEntity);
        publicWelfareTaskService.newTaskRecevieUser(taskRecevieUserEntity);
        PointEntity points = publicWelfareTaskService.selectPonitByUserId(taskModel.getReceiveUserId());
        PointEntity publisherPoints = publicWelfareTaskService.selectPonitByUserId(taskPublishUserEntity.getPublishUserId());
        //新用户初始积分100
        points = checkPint(points,taskModel.getReceiveUserId());
        publisherPoints = checkPint(publisherPoints,taskPublishUserEntity.getPublishUserId());
        //增加任务积分
        TaskEntity taskEntity = publicWelfareTaskService.queryTask(taskModel.getServiceId());
        String taskPoint = taskEntity.getPoints();
        if(taskPoint!=null&&!taskPoint.contains(".")){
            points.setPoints(String.valueOf(Integer.valueOf(taskPoint)+Integer.valueOf(points.getPoints())));
            publisherPoints.setPoints(String.valueOf(Integer.valueOf(publisherPoints.getPoints())-Integer.valueOf(taskPoint)));
            publicWelfareTaskService.updatePonit(points);
            publicWelfareTaskService.updatePonit(publisherPoints);
        }
        taskEntity.setStatus("已完成");
        taskEntity.setUpdatedAt(updatedTime);
        publicWelfareTaskService.updateTask(taskEntity);//更新状态
        return JSON.toJSONString(BuildResponse.success(taskPublishUserEntity));

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
            return JSON.toJSONString(BuildResponse.fail("当前任务不可审核"));
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
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setStatus("执行中");
        taskEntity.setUpdatedAt(updatedTime);
        publicWelfareTaskService.updateTask(taskEntity);//更新状态
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
        PointEntity points = publicWelfareTaskService.selectPonitByUserId(userId);
        //新用户初始积分100
        points = checkPint(points,userId);
        Map<String,Object> returnMap = BuildResponse.success();
        returnMap.put("points",points);
        return JSON.toJSONString(returnMap);

    }

    /**
     * 按照金币进行排行 TODO 用户信息
     * @param
     * @return
     */
    @RequestMapping(value = "rankByGold",method = RequestMethod.POST)
    public String rankByGold(){
        List<PointRankForm> result = publicWelfareTaskService.selectTopPointUser();
        return JSON.toJSONString(BuildResponse.success(result));

    }
}
