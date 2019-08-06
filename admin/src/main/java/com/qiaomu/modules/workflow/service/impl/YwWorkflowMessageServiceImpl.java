package com.qiaomu.modules.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.workflow.dao.YwWorkflowMessageDao;
import com.qiaomu.modules.workflow.entity.YwWorkflowMessage;
import com.qiaomu.modules.workflow.service.YwWorkflowMessageService;
import com.qiaomu.modules.sys.service.SysDictService;
import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.sys.service.UserExtendService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                processMessage.setPhoneOneName(userExtendService.getRealNamesByUserIdsAndCommunityId(processMessage.getPhoneOneId(),processMessage.getCommunityId(),","));
            }
            if(processMessage.getPhoneTwoId() !=null ){
                processMessage.setPhoneTwoName(userExtendService.getRealNamesByUserIdsAndCommunityId(processMessage.getPhoneTwoId(),processMessage.getCommunityId(),","));
            }
           if(processMessage.getReportPersonId() !=null ){
               processMessage.setReportPersonName(userExtendService.getRealNamesByUserIdsAndCommunityId(processMessage.getReportPersonId(),processMessage.getCommunityId(),","));
           }
            if(processMessage.getSuperintendentId() !=null){
                processMessage.setSuperintendentName(userExtendService.getRealNamesByUserIdsAndCommunityId(processMessage.getSuperintendentId(),processMessage.getCommunityId(),","));
            }

        }
        return new PageUtils(page);
    }

    @Transactional
    public void save(YwWorkflowMessage process) {
        if (process.getId() != null) {
            YwWorkflowMessage workflowMessage = this.baseMapper.selectById(process.getId());
            workflowMessage.setPhoneOneId(process.getPhoneOneId());
            workflowMessage.setPhoneTwoId(process.getPhoneTwoId());
            workflowMessage.setReportPersonId(process.getReportPersonId());
            workflowMessage.setSuperintendentId(process.getSuperintendentId());
            workflowMessage.setProcessName(process.getProcessName());
            workflowMessage.setCommunityId(process.getCommunityId());
            workflowMessage.setDicValue(process.getDicValue());
            this.baseMapper.updateAllColumnById(workflowMessage);
        } else {
            process.setCreateTime(new Date());
            insert(process);
        }
    }


    public YwWorkflowMessage getById(Long id) {
        YwWorkflowMessage processMessage = selectById(id);
        processMessage.setCommunityName(this.communityService.queryById(processMessage.getCommunityId()).getName());
        processMessage.setDicValueName(this.dictService.getdictCodeByTypeValue(processMessage.getDicValue(), "property_process"));
        if(processMessage.getPhoneOneId() !=null){
            processMessage.setPhoneOneName(userExtendService.getRealNamesByUserIdsAndCommunityId(processMessage.getPhoneOneId(),processMessage.getCommunityId(),","));
        }
        if(processMessage.getPhoneTwoId() !=null ){
            processMessage.setPhoneTwoName(userExtendService.getRealNamesByUserIdsAndCommunityId(processMessage.getPhoneTwoId(),processMessage.getCommunityId(),","));
        }
        if(processMessage.getReportPersonId() !=null ){
            processMessage.setReportPersonName(userExtendService.getRealNamesByUserIdsAndCommunityId(processMessage.getReportPersonId(),processMessage.getCommunityId(),","));
        }
        if(processMessage.getSuperintendentId() !=null){
            processMessage.setSuperintendentName(userExtendService.getRealNamesByUserIdsAndCommunityId(processMessage.getSuperintendentId(),processMessage.getCommunityId(),","));
        }
        return processMessage;
    }

    @Override
    public List<YwWorkflowMessage> getAll(YwWorkflowMessage workflowMessage) {
        List<YwWorkflowMessage> list = this.baseMapper.getAll(workflowMessage);
        for (YwWorkflowMessage message : list) {
            message.setCommunityName(this.communityService.queryById(message.getCommunityId()).getName());
            message.setDicValueName(this.dictService.getdictCodeByTypeValue(message.getDicValue(), "property_process"));
            if(message.getPhoneOneId() !=null){
                message.setPhoneOneName(userExtendService.getRealNamesByUserIdsAndCommunityId(message.getPhoneOneId(),message.getCommunityId(),","));
            }
            if(message.getPhoneTwoId() !=null ){
                message.setPhoneTwoName(userExtendService.getRealNamesByUserIdsAndCommunityId(message.getPhoneTwoId(),message.getCommunityId(),","));
            }
            if(message.getReportPersonId() !=null ){
                message.setReportPersonName(userExtendService.getRealNamesByUserIdsAndCommunityId(message.getReportPersonId(),message.getCommunityId(),","));
            }
            if(message.getSuperintendentId() !=null){
                message.setSuperintendentName(userExtendService.getRealNamesByUserIdsAndCommunityId(message.getSuperintendentId(),message.getCommunityId(),","));
            }

        }
        return list;
    }
}