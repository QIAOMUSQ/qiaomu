package com.qiaomu.modules.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.workflow.dao.UserWorkflowDao;
import com.qiaomu.modules.workflow.dao.YwWorkflowMessageDao;
import com.qiaomu.modules.workflow.entity.YwWorkflowMessage;
import com.qiaomu.modules.workflow.service.UserWorkflowService;
import com.qiaomu.modules.workflow.service.YwWorkflowMessageService;
import com.qiaomu.modules.sys.service.SysDictService;
import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.sys.service.UserExtendService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 16:07
 */
@Service
public class YwWorkflowMessageServiceImpl extends ServiceImpl<YwWorkflowMessageDao, YwWorkflowMessage>
        implements YwWorkflowMessageService {

    @Autowired
    private YwCommunityService communityService;

    @Autowired
    private UserExtendService userExtendService;

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysDictService dictService;

    @Autowired
    private UserWorkflowService userWorkflowService;


    public PageUtils queryPage(Map<String, Object> params) {
        Long companyId = null;
        String communityName = (String) params.get("communityName");
        String processName = (String) params.get("processName");
        String dicCode = (String) params.get("workflowType");
        //当没指定社区时选择全物业
        Long communityId = null;
        if(params.get("communityId") != null){
            //指定固定社区
            communityId = (Long) params.get("communityId");
        }else {
            //不指定社区，获取整个物业信息
             companyId = (Long) params.get("companyId");
        }
        Page<YwWorkflowMessage> page = selectPage(new Query(params).getPage(),
                new EntityWrapper()
                        .like(StringUtils.isNotBlank(processName), "process_name", processName)
                        .eq(companyId !=null,"COMPANY_ID", companyId)
                        .eq(communityId !=null, "community_id", communityId)
                        .eq(StringUtils.isNotBlank(dicCode),"DIC_VALUE",dicCode)
                        .addFilterIfNeed(params.get("sql_filter") != null,
                        (String) params.get("sql_filter"), new Object[0]));
        String name = null;
        for (YwWorkflowMessage processMessage : page.getRecords()) {
            processMessage.setCommunityName(this.communityService.queryById(processMessage.getCommunityId()).getName());
            processMessage.setDicValueName(this.dictService.getdictCodeByTypeValue(processMessage.getDicValue(), "property_process"));
            if(processMessage.getPhoneOneId() !=null){
                processMessage.setUserName(userService.getRealNameByIds(processMessage.getPhoneOneId()));
            }

        }
        return new PageUtils(page);
    }

    @Transactional
    public void save(YwWorkflowMessage process) {
        if (process.getId() != null) {
            YwWorkflowMessage workflowMessage = this.baseMapper.selectById(process.getId());
            workflowMessage.setPhoneOneId(process.getPhoneOneId());
            workflowMessage.setProcessName(process.getProcessName());
            workflowMessage.setCommunityId(process.getCommunityId());
            workflowMessage.setDicValue(process.getDicValue());
            this.baseMapper.updateAllColumnById(workflowMessage);
            //将流程和工作人员关联信息存库
            userWorkflowService.changeInfo(workflowMessage);
        } else {
            process.setCreateTime(new Date());
            insert(process);
            userWorkflowService.changeInfo(process);
        }

    }


    public YwWorkflowMessage getById(Long id) {
        YwWorkflowMessage processMessage = selectById(id);
        processMessage.setCommunityName(this.communityService.queryById(processMessage.getCommunityId()).getName());
        processMessage.setDicValueName(this.dictService.getdictCodeByTypeValue(processMessage.getDicValue(), "property_process"));
        return processMessage;
    }

    @Override
    public List<YwWorkflowMessage> getAll(YwWorkflowMessage workflowMessage) {
        List<YwWorkflowMessage> list = this.baseMapper.getAll(workflowMessage);
        for (YwWorkflowMessage message : list) {
            message.setDicValueName(this.dictService.getdictCodeByTypeValue(message.getDicValue(), "property_process"));
        }
        return list;
    }

    @Override
    public void deleteByCommunity(Long communityId) {
        baseMapper.deleteByCommunity(communityId);
    }


}