package com.qiaomu.modules.auditprocess.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.auditprocess.dao.YwWorkflowInfoDao;
import com.qiaomu.modules.auditprocess.entity.YwWorkflowInfo;
import com.qiaomu.modules.auditprocess.entity.YwWorkflowMessage;
import com.qiaomu.modules.auditprocess.service.YwWorkflowInfoService;
import com.qiaomu.modules.auditprocess.service.YwWorkflowMessageService;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.sys.service.SysDictService;
import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.sys.service.UserExtendService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.qiaomu.common.utils.StringCommonUtils.returnNullData;

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

    @Autowired
    private SysUserService userService;

    public PageUtils queryPage(Map<String, Object> params) {
        Long companyId = null;//
        String communityName = (String) params.get("communityName");
        String processName = (String) params.get("processName");
        String phone = (String) params.get("phone");
        String type = (String) params.get("type");
        String workflowType = (String) params.get("workflowType");//字典值

        Long communityId = null;
        //若不指定社区则查询该物业下所有社区
        if(params.get("communityId") != null){
            communityId = (Long) params.get("communityId");
        }else {
            companyId = (Long) params.get("companyId");
        }
        Long userId = null;
        if(StringUtils.isNotBlank(phone)){
             userId = userService.isExist(phone).getUserId();
        }


        Page<YwWorkflowInfo> page = selectPage(
                new Query(params).getPage(),
                new EntityWrapper()
                        .like(StringUtils.isNotBlank(processName), "process_name", processName)
                        .eq(companyId !=null,"COMPANY_ID", companyId)
                        .eq(userId != null ,"USER_ID",userId)
                        .eq(StringUtils.isNotBlank(workflowType),"workflow_type",workflowType)
                        .eq(StringUtils.isNotBlank(type),"type",type)
                        .eq(communityId !=null , "community_id", communityId)
                        .addFilterIfNeed(params.get("sql_filter") != null, (String) params.get("sql_filter"), new Object[0]));

        SysUserEntity user = null;
        for (YwWorkflowInfo workflowInfo : page.getRecords()) {
            YwWorkflowMessage workflowMessage = this.workflowMessageService.getById(workflowInfo.getWorkflowId());
            if(workflowMessage.getPhoneOneId() !=null){
                user = userService.queryById(workflowMessage.getPhoneOneId());
                workflowInfo.setDetailPhoneOneName(returnNullData(user.getRealName()));
                workflowInfo.setDetailPhoneOne(user.getUsername());
                user = null;
            }
            if(workflowMessage.getPhoneTwoId() !=null){
                user  = userService.queryById(workflowMessage.getPhoneTwoId());
                workflowInfo.setDetailPhoneTwoName(returnNullData(user.getRealName()));
                workflowInfo.setDetailPhoneTwo(user.getUsername());
                user = null;
            }

            if(workflowMessage.getReportPersonId() != null){
                user = userService.queryById(workflowMessage.getReportPersonId());
                workflowInfo.setDetailPhoneReportName(returnNullData(user.getRealName()));
                workflowInfo.setDetailPhoneReport(user.getUsername());
                user = null;
            }
            if(workflowMessage.getSuperintendentId() != null ){
                user = userService.queryById(workflowMessage.getSuperintendentId());
                workflowInfo.setSuperintendentName(returnNullData(user.getRealName()));
                workflowInfo.setSuperintendentPhone(user.getUsername());
                user = null;
            }
            workflowInfo.setProcessName(workflowMessage.getProcessName());
        }
        return new PageUtils(page);
    }


    /**
     * 保存流程信息
     * @param userPhone 用户手机
     * @param location 位置
     * @param detail    细节
     * @param pictureId 图片
     * @param serviceDate   上门维修时间
     * @param workflowId    流程ID
     * @param communityId 社区ID
     * @return
     */
    @Override
    public String saveWorkflowInfo(String userPhone, String location,
                                   String detail, String pictureId,
                                   String serviceDate, Long workflowId,Long communityId) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        YwWorkflowMessage workflowMessage = workflowMessageService.getById(workflowId);
        YwCommunity community =  communityService.queryById(communityId);
        YwWorkflowInfo workflow = new YwWorkflowInfo();

        workflow.setUserId(userService.isExist(userPhone).getUserId());
        workflow.setWorkflowId(workflowId);
        workflow.setLocation(location);
        workflow.setDetail(detail);
        workflow.setPictureId(pictureId);
        workflow.setWorkflowType(workflowMessage.getDicValue());
        workflow.setType("0");
        try{
            workflow.setServiceDate(sdf.parse(serviceDate));
            workflow.setCreateTime(new Date());
            workflow.setCompanyId(community.getCompanyId());
            workflow.setCommunityId(communityId);
            this.insert(workflow);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 更新用户流程信息
     * @param userPhone 用户手机
     * @param detailOpinionOne  第一处理人号码
     * @param detailOpinionTwo 第二处理人
     * @param detailOpinionReport  上报人
     * @param userOpinion   用户意见
     * @param type  流程状态 0：申请 1：一级受理 11：一级受理完成 2：二级受理 21：二级受理完成  3：上报  4：通过  5：不通过 6:终止
     * @param id 流程信息ID
     */
    @Override
    public Boolean updateUserWorkflowInfo(String userPhone, String detailOpinionOne, String detailOpinionTwo, String detailOpinionReport, String userOpinion, String type, Long id) {

       try {
           YwWorkflowInfo workflowInfo = this.selectById(id);
            if(StringUtils.isNotBlank(detailOpinionOne)){
                workflowInfo.setDetailOpinionOne(detailOpinionOne);
                workflowInfo.setDetailOneDate(new Date());
            }
            if (StringUtils.isNotBlank(detailOpinionTwo)){
                workflowInfo.setDetailOpinionTwo(detailOpinionTwo);
                workflowInfo.setDetailTwoDate(new Date());
            }
            if (StringUtils.isNotBlank(detailOpinionReport)){
                workflowInfo.setDetailOpinionReport(detailOpinionReport);
                workflowInfo.setReportDate(new Date());
            }
            if(StringUtils.isNotBlank(userOpinion)){
                workflowInfo.setUserOpinion(userOpinion);
            }
            workflowInfo.setType(type);
            updateById(workflowInfo);
           return true;
       }catch (Exception e){
           e.printStackTrace();
           return false;
       }
    }
}