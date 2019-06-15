package com.qiaomu.modules.auditprocess.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.auditprocess.dao.YwWorkflowInfoDao;
import com.qiaomu.modules.auditprocess.entity.YwWorkflowInfo;
import com.qiaomu.modules.auditprocess.service.YwWorkflowInfoService;
import com.qiaomu.modules.auditprocess.service.YwWorkflowMessageService;
import com.qiaomu.modules.sys.service.SysDictService;
import com.qiaomu.modules.sys.service.YwCommunityService;
import com.qiaomu.modules.sys.service.UserExtendService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 16:05
 */
@Service
public class YwWorkflowInfoServiceImpl extends ServiceImpl<YwWorkflowInfoDao, YwWorkflowInfo>
        implements YwWorkflowInfoService {

    @Autowired
    private YwCommunityService communityService;

    @Autowired
    private UserExtendService userExtendService;

    @Autowired
    private YwWorkflowMessageService workflowMessageService;

    @Autowired
    private SysDictService dictService;

    public PageUtils queryPage(Map<String, Object> params) {
        Integer companyId = (Integer) params.get("companyId");
        String communityName = (String) params.get("communityName");
        String processName = (String) params.get("processName");
        List<Integer> communityId = this.communityService.getCommunityIdList(communityName, companyId);
        Page<YwWorkflowInfo> page = selectPage(new Query(params)
                .getPage(), new EntityWrapper()
                .like(StringUtils.isNotBlank(processName),
                        "process_name", processName)
                .eq("COMPANY_ID", companyId)
                .in(communityId
                        .size() > 0, "community_id", communityId)
                .addFilterIfNeed(params
                                .get("sql_filter") != null,
                        (String) params.get("sql_filter"), new Object[0]));

        for (YwWorkflowInfo processCheck : page.getRecords()) {
            processCheck.setClientPhone(getUserByPhone(processCheck.getClientPhone()));
            processCheck.setProcessName(this.workflowMessageService.getById(processCheck.getWorkflowId()).getProcessName());
        }
        return new PageUtils(page);
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

    @Override
    public String saveWorkflowInfo(String userPhone, String location,
                                   String detail, String pictureId,
                                   String serviceDate, Integer workflowId,Integer companyId,Integer communityId) {
        DateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        YwWorkflowInfo workflow = new YwWorkflowInfo();
        workflow.setClientPhone(userPhone);
        workflow.setWorkflowId(workflowId);
        workflow.setLocation(location);
        workflow.setDetail(detail);
        workflow.setPictureId(pictureId);
        try{
            workflow.setServiceDate(sdf.parse(serviceDate));
            workflow.setCompanyId(companyId);
            workflow.setCommunityId(communityId);
            this.insert(workflow);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
}