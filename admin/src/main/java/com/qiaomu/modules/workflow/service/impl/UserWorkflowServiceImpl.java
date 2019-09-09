package com.qiaomu.modules.workflow.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.modules.workflow.dao.UserWorkflowDao;
import com.qiaomu.modules.workflow.entity.UserWorkflow;
import com.qiaomu.modules.workflow.entity.YwWorkflowMessage;
import com.qiaomu.modules.workflow.service.UserWorkflowService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-05 23:22
 */
@Service
public class UserWorkflowServiceImpl extends ServiceImpl<UserWorkflowDao,UserWorkflow> implements UserWorkflowService {

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void changeInfo(YwWorkflowMessage process) {
        String userId = "";
        if(StringUtils.isNotBlank(process.getPhoneOneId())){
            userId += process.getPhoneOneId()+",";
        }
        if(userId.length()>0){
            userId = userId.substring(0,userId.length()-1);
        }
        insertInfo(userId,process.getId(),process.getCommunityId());
    }

    private void insertInfo(String userId,Long workflowId,Long communityId){
        List<String> ids = Arrays.asList(userId.split(","));

        List<UserWorkflow> info = this.baseMapper.selectByworkflowId(workflowId);
        List<String> insertUserId = new ArrayList<>();
        List<Long> deleteId = new ArrayList<>();
        List<String> deleteUserId = new ArrayList<>();
        for (UserWorkflow workflow : info){
            // 1、查找对比出要删除的对应关系
            if (!ids.contains(workflow.getUserId().toString())){
                deleteUserId.add(workflow.getUserId().toString());
                deleteId.add(workflow.getId());
            }
        }
        for (String id : ids){
            //2、查找对比新增的对应关系
            if(!deleteUserId.contains(id)){
                insertUserId.add(id);
            }
        }
        if(deleteId.size()>0){
            this.baseMapper.deleteBatchIds(deleteId);
        }
        for (String id: insertUserId){
            UserWorkflow workflow=new UserWorkflow();
            workflow.setUserId(Long.valueOf(id));
            workflow.setWorkflowId(workflowId);
            workflow.setCommunityId(communityId);
            this.baseMapper.insert(workflow);
        }
    }

    @Override
    public List<UserWorkflow> getUserWorkflows(Long userId, Long communityId) {
        UserWorkflow workflow=new UserWorkflow();
        workflow.setCommunityId(communityId);
        workflow.setUserId(userId);
        return this.baseMapper.getUserWorkflows(workflow);
    }

    @Override
    public void deleteByCommunity(Long communityId) {
        baseMapper.deleteByCommunity(communityId);
    }

}
