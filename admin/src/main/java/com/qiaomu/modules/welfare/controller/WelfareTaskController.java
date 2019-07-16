package com.qiaomu.modules.welfare.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.BuildResponse;

import com.qiaomu.common.utils.CommonUtils;
import com.qiaomu.common.utils.DateUtils;
import com.qiaomu.common.validator.Assert;
import com.qiaomu.modules.welfare.bussiness.WelfareBussiness;
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
    private WelfareBussiness welfareBussiness;

    /**
     * 发布任务
     * @param
     * @return
     */
    @RequestMapping(value = "publishTask",method = RequestMethod.POST)
    public String publishTask(HttpServletRequest request){
        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
        welfareBussiness.publishTask(req);
        return JSON.toJSONString(BuildResponse.success());

    }

    /**
     * 查询所有审核中的任务
     * @param
     * @return
     */
    @RequestMapping(value = "queryAllSubmitReviewTask",method = RequestMethod.POST)
    public String queryAllSubmitReviewTask(String receiveUserId){
        List<TaskEntity>  tasks = welfareBussiness.queryAllSubmitReviewTask(receiveUserId);
        return JSON.toJSONString(BuildResponse.success(tasks));

    }
    /**
     * 查询所有发布的任务
     * @param
     * @return
     */
    @RequestMapping(value = "queryAllTask",method = RequestMethod.POST)
    public String queryAllTask(String communityId){
        List<TaskEntity>  tasks = welfareBussiness.queryAllTask(communityId);
        return JSON.toJSONString(BuildResponse.success(tasks));

    }
    /**
     * 查询所有执行中的任务
     * @param
     * @return
     */
    @RequestMapping(value = "queryAllRuningTask",method = RequestMethod.POST)
    public String queryAllRuningTask(String userId){
        Map<String,Object> tasks = welfareBussiness.queryAllRuningTask(userId);
        return JSON.toJSONString(BuildResponse.success(tasks));

    }

    /**
     * 查询所有已完成的任务
     * @param
     * @return
     */
    @RequestMapping(value = "queryAllCompleteTask",method = RequestMethod.POST)
    public String queryAllCompleteTask(String userId){
        List<TaskEntity>  tasks = welfareBussiness.queryAllCompleteTask(userId);
        return JSON.toJSONString(BuildResponse.success(tasks));

    }
    /**
     * 查询所有待审核的任务
     * @param
     * @return
     */
    @RequestMapping(value = "queryAllunReviewTask",method = RequestMethod.POST)
    public String queryAllunReviewTask(String publishUserId){
        List<TaskEntity>  tasks = welfareBussiness.queryAllunReviewTask(publishUserId);
        return JSON.toJSONString(BuildResponse.success(tasks));

    }

    /**
     * 查询用户创建的所有任务
     * @param
     * @return
     */
    @RequestMapping(value = "queryAllCreatedTask",method = RequestMethod.POST)
    public String queryAllCreatedTask(String publishUserId){
        List<TaskEntity>  tasks = welfareBussiness.queryAllCreatedTask(publishUserId);
        return JSON.toJSONString(BuildResponse.success(tasks));

    }

    /**
     * 查询用户领取的所有任务
     * @param
     * @return
     */
    @RequestMapping(value = "queryAllGetTask",method = RequestMethod.POST)
    public String queryAllGetTask(String receiveUserId){
        List<TaskEntity>  tasks = welfareBussiness.queryAllGetTask(receiveUserId);
        return JSON.toJSONString(BuildResponse.success(tasks));
    }

    /**
     * 领取任务
     * @param
     * @return
     */
    @RequestMapping(value = "getTask",method = RequestMethod.POST)
    public String getTask(TaskModel taskModel){
        welfareBussiness.getTask(taskModel);
        return JSON.toJSONString(BuildResponse.success());

    }

    /**
     * 取消领取任务
     * @param
     * @return
     */
    @RequestMapping(value = "cancelGetTask",method = RequestMethod.POST)
    public String cancelGetTask(TaskModel taskModel){
       welfareBussiness.cancelGetTask(taskModel);
        return JSON.toJSONString(BuildResponse.success());

    }

    /**
     * 取消发布的任务
     * @param
     * @return
     */
    @RequestMapping(value = "cancelPublishTask",method = RequestMethod.POST)
    public String cancelPublishTask(TaskModel taskModel){
        welfareBussiness.cancelPublishTask(taskModel);
        return JSON.toJSONString(BuildResponse.success());

    }

    /**
     * 提交审核
     * @param
     * @return
     */
    @RequestMapping(value = "submitReview",method = RequestMethod.POST)
    public String submitReview(TaskModel taskModel){
        TaskRecevieUserEntity taskRecevieUserEntity = welfareBussiness.submitReview(taskModel);
        return JSON.toJSONString(BuildResponse.success(taskRecevieUserEntity));

    }
    /**
     * 审核通过
     * @param
     * @return
     */
    @RequestMapping(value = "reviewSuccess",method = RequestMethod.POST)
    public String reviewSuccess(TaskModel taskModel){
        TaskPublishUserEntity taskPublishUserEntity = welfareBussiness.reviewSuccess(taskModel);
        return JSON.toJSONString(BuildResponse.success(taskPublishUserEntity));

    }


    /**
     * 审核不通过
     * @param
     * @return
     */
    @RequestMapping(value = "reviewFail",method = RequestMethod.POST)
    public String reviewFail(TaskModel taskModel){
        TaskPublishUserEntity taskPublishUserEntity = welfareBussiness.reviewFail(taskModel);
        return JSON.toJSONString(BuildResponse.success(taskPublishUserEntity));

    }

    /**
     * 统计个人金币
     * @param
     * @return
     */
    @RequestMapping(value = "statisticGold",method = RequestMethod.POST)
    public String statisticGold(String userId){
        Map<String,Object> returnMap = welfareBussiness.statisticGold(userId);
        return JSON.toJSONString(returnMap);

    }

    /**
     * 按照金币进行排行
     * @param
     * @return
     */
    @RequestMapping(value = "rankByGold",method = RequestMethod.POST)
    public String rankByGold(String communityId){
        List<PointRankForm> result = welfareBussiness.rankByGold(communityId);
        return JSON.toJSONString(BuildResponse.success(result));
    }
}
