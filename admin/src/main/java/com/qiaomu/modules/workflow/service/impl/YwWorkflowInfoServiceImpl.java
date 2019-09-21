package com.qiaomu.modules.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.exception.RRException;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.article.exception.CommentException;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.UserExtend;
import com.qiaomu.modules.sys.service.SysFileService;
import com.qiaomu.modules.workflow.dao.YwWorkflowInfoDao;
import com.qiaomu.modules.workflow.entity.UserWorkflow;
import com.qiaomu.modules.workflow.entity.YwWorkflowInfo;
import com.qiaomu.modules.workflow.entity.YwWorkflowMessage;
import com.qiaomu.modules.workflow.service.UserWorkflowService;
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
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Autowired
    private UserWorkflowService userWorkflowService;

    public PageUtils queryPage(Map<Object, Object> params) {
        YwWorkflowInfo info = new YwWorkflowInfo();
        Long companyId = null;//
        String communityName = (String) params.get("communityName");
        if (StringUtils.isNotBlank(communityName)){
            info.setCommunityIds(communityService.getCommunityIdList(communityName,null));
        }

        String type = (String) params.get("type");
        String workflowType = (String) params.get("workflowType");//字典值

        Long communityId = null;
        //若不指定社区则查询该物业下所有社区
        if(params.get("communityId") != null){
            communityId = (Long) params.get("communityId");
            info.setCommunityId(communityId);
        }else {
            companyId = (Long) params.get("companyId");
            info.setCompanyId(companyId);
        }
        if (StringUtils.isNotBlank(workflowType)){
            info.setWorkflowType(workflowType);
        }
        if(StringUtils.isNotBlank(type)){
            info.setType(type);
        }
        if(params.get("userId") != null){
            Long  userId = (Long)params.get("userId");
            info.setUserId(userId);
        }

        //获取用户社区内真实姓名

        Page<YwWorkflowInfo> page = new Query(params).getPage();// 当前页，总条
        page.setRecords(this.baseMapper.selectPageAll(page,info));
        for (YwWorkflowInfo workflowInfo : page.getRecords()) {
           // YwWorkflowMessage workflowMessage = this.workflowMessageService.getById(workflowInfo.getWorkflowId());
            if(workflowInfo.getPhoneOneId() !=null){
                workflowInfo.setDetailPhoneOneName(userService.getRealNameByIds(workflowInfo.getPhoneOneId()));
               // workflowInfo.setDetailPhoneOne(workflowMessage.getPhoneOneId());
            }

           // workflowInfo.setProcessName(workflowMessage.getProcessName());
            String user = userService.getRealNameByIds(workflowInfo.getUserId().toString());
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
            workflow.setPictureId(JSON.toJSONString(fileService.imageUrls(request)));
            workflow.setWorkflowType(workflowMessage.getDicValue());
            workflow.setType("0");
            workflow.setStatus("0");
            workflow.setServiceDate(serviceDate);
            workflow.setCreateTime(new Date());
            //workflow.setCompanyId(community.getCompanyId());
            workflow.setCommunityId(communityId);
            this.insert(workflow);
            findPushUser(workflow,"0");
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            throw new RRException("社区异常");
        }
    }


    /**
     *
     * @param opinionOne  第一处理人意见
     * @param userOpinion   用户意见
     * @param type 流程状态 0：申请 1：处理人员处理 2: 处理完成
     * @param id 流程信息ID
     * @return
     */
    @Override
    public Boolean updateUserWorkflowInfo( String opinionOne, String userOpinion, String type, Long id,String starType) {
        try {
            YwWorkflowInfo workflowInfo = this.selectById(id);
            if(StringUtils.isNotBlank(opinionOne)){
                workflowInfo.setDetailOpinionOne(opinionOne);
                workflowInfo.setDetailOneDate(new Date());
            }
            if(StringUtils.isNotBlank(userOpinion)){
                workflowInfo.setUserOpinion(userOpinion);
                workflowInfo.setFinalityDate(new Date());
                workflowInfo.setStatus("1");
            }
            if(StringUtils.isNotBlank(starType)){
                workflowInfo.setStarType(starType);
            }
            workflowInfo.setType(type);
            updateById(workflowInfo);
            findPushUser(workflowInfo,type);
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

        //type  流程状态  0：申请 1：处理人员完成 2: 处理完成
        if (info.getType().equals("0")){
            //用户建立新流程申请
            String idString =workflowMessage.getPhoneOneId();
            pushRedisMessageService.pushMessage(info.getUserId(),idString,"报修申请","0","请及时处理新流程",null);
        }else if(info.getType().equals("1")){
            //一级处理人接受处理提醒用户
            pushRedisMessageService.pushMessage(info.getUserId(),info.getUserId()+"","报修申请","0","您的申请工作人员已经受理完成",null);
        }else if(info.getType().equals("2")){
            String idString =workflowMessage.getPhoneOneId();
            pushRedisMessageService.pushMessage(info.getUserId(),idString,"报修申请","0","报修申请已完结",null);
        }

    }


    @Override
    public List<YwWorkflowInfo> getAll(Long userId, Long communityId, String workflowType, String type,String status) {
        SysUserEntity sysUserEntity = userService.queryById(userId);
        if(sysUserEntity==null||sysUserEntity.getRealName()==null||sysUserEntity.getRealName().isEmpty()){
            throw new CommentException("请先实名认证！");
        }

        UserExtend userExtendQ = new UserExtend();
        userExtendQ.setUserId(Long.valueOf(sysUserEntity.getUserId()));
        userExtendQ.setCommunityId(Long.valueOf(communityId));
        UserExtend userExtend = userExtendService.queryUserExtend(userExtendQ);
        if(userExtend==null||userExtend.getCommunityId()==null){
            throw new CommentException("请认证该小区！");
        }
        YwWorkflowInfo condition = new YwWorkflowInfo();
        condition.setUserId(userId);
        condition.setCommunityId(communityId);
        condition.setWorkflowType(workflowType);
        condition.setType(type);
        condition.setStatus(status);
        List<YwWorkflowInfo> infoList = this.baseMapper.getAll(condition);
        for (YwWorkflowInfo Info : infoList) {
           // YwWorkflowMessage workflowMessage = this.workflowMessageService.getById(Info.getWorkflowId());
            if(Info.getPhoneOneId() !=null){
                Info.setDetailPhoneOneName(userService.getRealNameByIds(Info.getPhoneOneId()));
                Info.setDetailPhoneOne(Info.getPhoneOneId());
                Info.setDetailPhone(userService.getUserNameByIds(Info.getPhoneOneId(),","));
            }

            Info.setProcessName(Info.getProcessName());
        }
        return infoList;
    }

    @Override
    public YwWorkflowInfo selectById(Serializable id) {
        YwWorkflowInfo info = super.selectById(id);
        YwWorkflowMessage workflowMessage = this.workflowMessageService.getById(info.getWorkflowId());
        if(workflowMessage.getPhoneOneId() !=null){
            info.setDetailPhoneOneName(userService.getRealNameByIds(workflowMessage.getPhoneOneId()));
            info.setDetailPhoneOne(workflowMessage.getPhoneOneId());
            info.setDetailPhone(userService.getUserNameByIds(workflowMessage.getPhoneOneId(),","));
        }

        info.setProcessName(workflowMessage.getProcessName());
        return  info;
    }

    @Override
    public List<YwWorkflowInfo> getUserDetailWorkflow(Long userId, Long communityId) {
        YwWorkflowInfo workflowInfo = new YwWorkflowInfo();
        workflowInfo.setCommunityId(communityId);
        workflowInfo.setUserId(userId);
        return this.baseMapper.getAllWorker(workflowInfo);
    }
}