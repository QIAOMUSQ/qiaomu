package com.qiaomu.modules.auditprocess.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.auditprocess.dao.YwWorkflowMessageDao;
import com.qiaomu.modules.auditprocess.entity.YwWorkflowMessage;
import com.qiaomu.modules.auditprocess.service.YwWorkflowMessageService;
import com.qiaomu.modules.sys.service.SysDictService;
import com.qiaomu.modules.sys.service.YwCommunityService;
import com.qiaomu.modules.sys.service.UserExtendService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private SysDictService dictService;

    public PageUtils queryPage(Map<String, Object> params) {
        Integer companyId = (Integer) params.get("companyId");
        String communityName = (String) params.get("communityName");
        String processName = (String) params.get("processName");
        List<Integer> communityId = this.communityService.getCommunityIdList(communityName, companyId);
        Page<YwWorkflowMessage> page = selectPage(new Query(params)
                .getPage(), new EntityWrapper()
                .like(StringUtils.isNotBlank(processName),
                        "process_name", processName)
                .eq("COMPANY_ID", companyId)
                .in(communityId
                        .size() > 0, "community_id", communityId)
                .addFilterIfNeed(params
                                .get("sql_filter") != null,
                        (String) params.get("sql_filter"), new Object[0]));

        for (YwWorkflowMessage processMessage : page.getRecords()) {
            processMessage.setPhoneOne(getUserByPhone(processMessage.getPhoneOne()));
            processMessage.setPhoneTwo(getUserByPhone(processMessage.getPhoneTwo()));
            processMessage.setReportPerson(getUserByPhone(processMessage.getReportPerson()));
            processMessage.setCommunityName(this.communityService.queryById(processMessage.getCommunityId()).getName());
            processMessage.setProcessType(this.dictService.getdictCodeByTypeValue(processMessage.getProcessType(), "property_process"));
        }
        return new PageUtils(page);
    }

    public void save(YwWorkflowMessage processMessage) {
        if (processMessage.getId() != null) {
            updateById(processMessage);
        } else {
            processMessage.setCreateTime(new Date());
            insert(processMessage);
        }
    }

    private String getUserByPhone(String phone) {
        String name = "";
        String[] phones = phone.split("_");
        for (String i : phones) {
            name = name + this.userExtendService.getUserExtend(i).getRealName() + "、";
        }
        name = name.substring(0, name.length() - 1);
        return name;
    }

    public YwWorkflowMessage getById(Integer id) {
        YwWorkflowMessage processMessage = (YwWorkflowMessage) selectById(id);
        processMessage.setPhoneOneName(getUserByPhone(processMessage.getPhoneOne()));
        processMessage.setPhoneTwoName(getUserByPhone(processMessage.getPhoneTwo()));
        processMessage.setReportPersonName(getUserByPhone(processMessage.getReportPerson()));
        processMessage.setCommunityName(this.communityService.queryById(processMessage.getCommunityId()).getName());
        return processMessage;
    }
}