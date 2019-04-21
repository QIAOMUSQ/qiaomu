package com.qiaomu.modules.auditprocess.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.auditprocess.dao.YwAuditProcesscheckDao;
import com.qiaomu.modules.auditprocess.entity.YwAuditProcessCheck;
import com.qiaomu.modules.auditprocess.service.YwAuditProcessCheckService;
import com.qiaomu.modules.auditprocess.service.YwAuditProcessMessageService;
import com.qiaomu.modules.sys.service.SysDictService;
import com.qiaomu.modules.sys.service.YwCommunityService;
import com.qiaomu.modules.sys.service.YwUserExtendService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 16:05
 */
@Service
public class YwAuditProcessCheckServiceImpl extends ServiceImpl<YwAuditProcesscheckDao, YwAuditProcessCheck>
        implements YwAuditProcessCheckService
{

    @Autowired
    private YwCommunityService communityService;

    @Autowired
    private YwUserExtendService userExtendService;

    @Autowired
    private YwAuditProcessMessageService processMessageService;

    @Autowired
    private SysDictService dictService;

    public PageUtils queryPage(Map<String, Object> params)
    {
        Long companyId = (Long)params.get("companyId");
        String communityName = (String)params.get("communityName");
        String processName = (String)params.get("processName");
        List communityId = this.communityService.getCommunityIdList(communityName, companyId);
        Page<YwAuditProcessCheck> page = selectPage(new Query(params)
                .getPage(), new EntityWrapper()
                .like(StringUtils.isNotBlank(processName),
                        "process_name", processName)
                .eq("COMPANY_ID", companyId)
                .in(communityId
                        .size() > 0, "community_id", communityId)
                .addFilterIfNeed(params
                                .get("sql_filter") != null,
                        (String)params.get("sql_filter"), new Object[0]));

        for (YwAuditProcessCheck processCheck : page.getRecords()) {
            processCheck.setClientPhone(getUserByPhone(processCheck.getClientPhone()));
            processCheck.setProcessName(this.processMessageService.getById(processCheck.getProcessId()).getProcessName());
        }
        return new PageUtils(page);
    }

    private String getUserByPhone(String phone)
    {
        String name = "";
        String[] phones = phone.split("_");
        for (String i : phones) {
            name = name + this.userExtendService.getUserExtend(i).getRealName() + "、";
        }
        name = name.substring(0, name.length() - 1);
        return name;
    }
}