package com.qiaomu.modules.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.exception.RRException;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.sys.service.SysFileService;
import com.qiaomu.modules.workflow.dao.YwWorkflowInfoDao;
import com.qiaomu.modules.workflow.entity.YwWorkflowInfo;
import com.qiaomu.modules.workflow.entity.YwWorkflowMessage;
import com.qiaomu.modules.workflow.service.YwWorkflowInfoService;
import com.qiaomu.modules.workflow.service.YwWorkflowMessageService;
import com.qiaomu.modules.infopublish.entity.PushMessage;
import com.qiaomu.modules.infopublish.service.PushRedisMessageService;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.sys.service.SysDictService;
import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.sys.service.UserExtendService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description: 流程审核
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

    @Autowired
    private PushRedisMessageService pushRedisMessageService;

    @Autowired
    private SysFileService fileService;

    public PageUtils queryPage(Map<Object, Object> params) {
        Long companyId = null;//
        String communityName = (String) params.get("communityName");
        String processName = (String) params.get("processName");
        //String userId = (String) params.get("userId");
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
        if(params.get("userId") != null){
             userId = (Long)params.get("userId");
        }
        //获取用户社区内真实姓名

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
        for (YwWorkflowInfo workflowInfo : page.getRecords()) {
            YwWorkflowMessage workflowMessage = this.workflowMessageService.getById(workflowInfo.getWorkflowId());
            if(workflowMessage.getPhoneOneId() !=null){
                workflowInfo.setDetailPhoneOneName(userExtendService.getRealNamesByUserIdsAndCommunityId(workflowMessage.getPhoneOneId(),workflowMessage.getCommunityId(),","));
                workflowInfo.setDetailPhoneOne(workflowMessage.getPhoneOneId());
            }
            if(workflowMessage.getPhoneTwoId() !=null){
                workflowInfo.setDetailPhoneTwoName(userExtendService.getRealNamesByUserIdsAndCommunityId(workflowMessage.getPhoneTwoId(),workflowMessage.getCommunityId(),","));
                workflowInfo.setDetailPhoneTwo(workflowMessage.getPhoneTwoId() );
            }

            if(workflowMessage.getReportPersonId() != null){
                workflowInfo.setDetailPhoneReportName(userExtendService.getRealNamesByUserIdsAndCommunityId(workflowMessage.getReportPersonId(),workflowMessage.getCommunityId(),","));
                workflowInfo.setDetailPhoneReport(workflowMessage.getReportPersonId());
            }
            if(workflowMessage.getSuperintendentId() != null ){
                workflowInfo.setSuperintendentName(userExtendService.getRealNamesByUserIdsAndCommunityId(workflowMessage.getSuperintendentId(),workflowMessage.getCommunityId(),","));
                workflowInfo.setSuperintendentPhone(workflowMessage.getSuperintendentId());
            }
            workflowInfo.setProcessName(workflowMessage.getProcessName());
            String user = userExtendService.getRealNamesByUserIdsAndCommunityId(workflowInfo.getUserId().toString(),workflowMessage.getCommunityId(),",");
            if(user!=null){
                workflowInfo.setUserName(user);
            }

        }
        return new PageUtils(page);
    }


    /**
     * 保存流程信息
     * @param userId 用户手机
     * @param location 位置
     * @param detail    细节
     * @param pictureId 图片
     * @param serviceDate   上门维修时间
     * @param workflowId    流程ID
     * @param communityId 社区ID
     * @return
     */
    @Override
    @Transactional
    public String saveWorkflowInfo(Long userId, String location,
                                         String detail, String pictureId,
                                         String serviceDate, Long workflowId, Long communityId,HttpServletRequest request) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        YwWorkflowMessage workflowMessage = workflowMessageService.getById(workflowId);
        YwCommunity community =  communityService.queryById(communityId);
        //当社区未进行物业公司或者街道分配
        if(community.getCompanyId() == null){
            throw new RRException("社区未分配!");
        }

        try{
            YwWorkflowInfo workflow = new YwWorkflowInfo();

            workflow.setUserId(userId);
            workflow.setWorkflowId(workflowId);
            workflow.setLocation(location);
            workflow.setDetail(detail);
           // workflow.setPictureId(pictureId);
            workflow.setPictureId(JSON.toJSONString(fileService.imageUrls(request)));
            workflow.setWorkflowType(workflowMessage.getDicValue());
            workflow.setType("0");
            workflow.setServiceDate(serviceDate);
            workflow.setCreateTime(new Date());
            workflow.setCompanyId(community.getCompanyId());
            workflow.setCommunityId(communityId);
            this.insert(workflow);
          //  findPushUser(workflow,"0");
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            throw new RRException("社区异常");
        }
    }


    /**
     *
     * @param opinionSuperintendent 监管人意见
     * @param opinionOne  第一处理人号码
     * @param opinionTwo 第二处理人
     * @param opinionReport  上报人
     * @param userOpinion   用户意见
     * @param type 流程状态 0：申请 1：一级接受受理 11：一级受理完成 2：二级接受受理 21：二级受理完成  3：上报  4：通过  5：不通过 6:终止
     * @param id 流程信息ID
     * @return
     */
    @Override
    public Boolean updateUserWorkflowInfo(String opinionSuperintendent, String opinionOne, String opinionTwo, String opinionReport, String userOpinion, String type, Long id) {
        try {
            YwWorkflowInfo workflowInfo = this.selectById(id);
            if(StringUtils.isNotBlank(opinionOne)){
                workflowInfo.setDetailOpinionOne(opinionOne);
                workflowInfo.setDetailOneDate(new Date());
            }
            if (StringUtils.isNotBlank(opinionTwo)){
                workflowInfo.setDetailOpinionTwo(opinionTwo);
                workflowInfo.setDetailTwoDate(new Date());
            }
            if (StringUtils.isNotBlank(opinionReport)){
                workflowInfo.setDetailOpinionReport(opinionReport);
                workflowInfo.setReportDate(new Date());
            }
            if (StringUtils.isNotBlank(opinionSuperintendent)){
                workflowInfo.setFinalityOpinion(opinionSuperintendent);
                workflowInfo.setFinalityDate(new Date());
            }
            if(StringUtils.isNotBlank(userOpinion)){
                workflowInfo.setUserOpinion(userOpinion);
            }
            workflowInfo.setType(type);
            updateById(workflowInfo);
           // findPushUser(workflowInfo,type);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 推送信息
     * @param info
     * @param type 0：用户新增推送信息
     *             2：用户完成该阶段信息推送下一阶段处理人
     */
    private void findPushUser(YwWorkflowInfo info,String type){
        Long workFlow = info.getWorkflowId();
        YwWorkflowMessage workflowMessage = workflowMessageService.getById(workFlow);

        //type  流程状态 0：申请 1：一级接受受理 11：一级受理完成
        // 2：二级主管接受受理 21：二级受理完成  3：上报  4：通过  5：不通过 6:终止
        PushMessage message = new PushMessage();
        if (info.getType().equals("0")){
            //用户建立新流程申请
            String idString =workflowMessage.getPhoneOneId();
            pushRedisMessageService.pushMessage(info.getUserId(),idString,"报修申请","0","请及时处理新流程");
        }else if(info.getType().equals("1")){
            //一级处理人接受处理提醒用户
            pushRedisMessageService.pushMessage(info.getUserId(),info.getUserId()+"","报修申请","0","您的申请工作人员已经受理");
        }else if(info.getType().equals("11")){
            //一级处理人处理完成提醒用户
            pushRedisMessageService.pushMessage(info.getUserId(),info.getUserId()+"","报修申请","0","您的申请工作人员已经受理完成");
            String idString =workflowMessage.getPhoneTwoId();
            //当有下级处理人推送通知
            if(idString != null){
                pushRedisMessageService.pushMessage(info.getUserId(),idString,"报修申请","0","新流程需要您受理，请及时处理");
            }
        }else if(info.getType().equals("2")){
            //二级主管接受受理提醒用户
            pushRedisMessageService.pushMessage(info.getUserId(),info.getUserId()+"","报修申请","0","二级主管接受受理");
        }else if(info.getType().equals("21")){
            //二级主管完成受理，并提醒用户
            pushRedisMessageService.pushMessage(info.getUserId(),info.getUserId()+"","报修申请","0","二级主管完成受理");
            String idString =workflowMessage.getReportPersonId();
            if(idString !=null ){
                pushRedisMessageService.pushMessage(info.getUserId(),idString,"报修申请","0","新流程需要您受理，请及时处理");
            }
        }else if(info.getType().equals("3")){
            pushRedisMessageService.pushMessage(info.getUserId(),info.getUserId()+"","报修申请","0","您提交的申请流程信息已经上报到管理层");
        }else if(info.getType().equals("6")){
            pushRedisMessageService.pushMessage(info.getUserId(),info.getUserId()+"","报修申请","0","您提交的申请流程已经终止,有什么疑问请联系处理人员");
        }
    }


    @Override
    public List<YwWorkflowInfo> getAll(Long userId, Long communityId, String workflowType, String type) {
        YwWorkflowInfo condition = new YwWorkflowInfo();
        condition.setUserId(userId);
        condition.setCommunityId(communityId);
        condition.setWorkflowType(workflowType);
        condition.setType(type);
        List<YwWorkflowInfo> infoList = this.baseMapper.getAll(condition);
        for (YwWorkflowInfo Info : infoList) {
            YwWorkflowMessage workflowMessage = this.workflowMessageService.getById(Info.getWorkflowId());
            if(workflowMessage.getPhoneOneId() !=null){
                Info.setDetailPhoneOneName(userExtendService.getRealNamesByUserIdsAndCommunityId(workflowMessage.getPhoneOneId(),workflowMessage.getCommunityId(),","));
                Info.setDetailPhoneOne(workflowMessage.getPhoneOneId());
            }
            if(workflowMessage.getPhoneTwoId() !=null){
                Info.setDetailPhoneTwoName(userExtendService.getRealNamesByUserIdsAndCommunityId(workflowMessage.getPhoneTwoId(),workflowMessage.getCommunityId(),","));
                Info.setDetailPhoneTwo(workflowMessage.getPhoneTwoId() );
            }

            if(workflowMessage.getReportPersonId() != null){
                Info.setDetailPhoneReportName(userExtendService.getRealNamesByUserIdsAndCommunityId(workflowMessage.getReportPersonId(),workflowMessage.getCommunityId(),","));
                Info.setDetailPhoneReport(workflowMessage.getReportPersonId());
            }
            if(workflowMessage.getSuperintendentId() != null ){
                Info.setSuperintendentName(userExtendService.getRealNamesByUserIdsAndCommunityId(workflowMessage.getSuperintendentId(),workflowMessage.getCommunityId(),","));
                Info.setSuperintendentPhone(workflowMessage.getSuperintendentId());
            }
            Info.setProcessName(workflowMessage.getProcessName());
        }
        return infoList;
    }
}