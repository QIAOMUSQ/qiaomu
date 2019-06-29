package com.qiaomu.modules.auditprocess.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.common.utils.StringCommonUtils;
import com.qiaomu.modules.auditprocess.dao.YwWorkflowMessageDao;
import com.qiaomu.modules.auditprocess.entity.YwWorkflowMessage;
import com.qiaomu.modules.auditprocess.service.YwWorkflowMessageService;
import com.qiaomu.modules.sys.service.SysDictService;
import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.sys.service.UserExtendService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.qiaomu.common.utils.StringCommonUtils.returnNullData;

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
            processMessage.setDicValue(this.dictService.getdictCodeByTypeValue(processMessage.getDicValue(), "property_process"));
            if(processMessage.getPhoneOneId() !=null){
                name =  userService.queryById(processMessage.getPhoneOneId()).getRealName();
                processMessage.setPhoneOneName(returnNullData(name));
                name = null;
            }
            if(processMessage.getPhoneTwoId() !=null ){
                name = userService.queryById(processMessage.getPhoneTwoId()).getRealName();
                processMessage.setPhoneTwoName(returnNullData(name));
                name = null;
            }
           if(processMessage.getReportPersonId() !=null ){
               name = userService.queryById(processMessage.getReportPersonId()).getRealName();
               processMessage.setReportPersonName(returnNullData(name));
               name = null;
           }
            if(processMessage.getSuperintendentId() !=null){
                name = userService.queryById(processMessage.getSuperintendentId()).getRealName();
                processMessage.setSuperintendentName(returnNullData(name));
                name = null;
            }

        }
        return new PageUtils(page);
    }

    @Transactional
    public void save(YwWorkflowMessage processMessage) {
       /* processMessage.setPhoneOneName(userExtendService.getUserByPhone(processMessage.getPhoneOne()));
        processMessage.setPhoneTwoName(userExtendService.getUserByPhone(processMessage.getPhoneTwo()));
        processMessage.setReportPersonName(userExtendService.getUserByPhone(processMessage.getReportPerson()));
        processMessage.setSuperintendentName(userExtendService.getUserByPhone(processMessage.getSuperintendentPhone()));
*/
        if (processMessage.getId() != null) {
            updateById(processMessage);
        } else {
            processMessage.setCreateTime(new Date());
            insert(processMessage);
        }
    }


    public YwWorkflowMessage getById(Long id) {
        YwWorkflowMessage processMessage = (YwWorkflowMessage) selectById(id);
       processMessage.setCommunityName(this.communityService.queryById(processMessage.getCommunityId()).getName());
        return processMessage;
    }

}