package com.qiaomu.modules.propertycompany.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qiaomu.modules.infopublish.entity.CarportEntity;
import com.qiaomu.modules.infopublish.entity.InvitationEntity;
import com.qiaomu.modules.infopublish.service.CarportService;
import com.qiaomu.modules.infopublish.service.InvitationService;
import com.qiaomu.modules.propertycompany.dao.CommunityAdvertiseDao;
import com.qiaomu.modules.propertycompany.dao.LoginStatisticsDao;
import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.sys.service.SysFileService;
import com.qiaomu.modules.sys.service.UserExtendService;
import com.qiaomu.modules.workflow.entity.YwWorkflowInfo;
import com.qiaomu.modules.workflow.service.UserWorkflowService;
import com.qiaomu.modules.workflow.service.YwWorkflowInfoService;
import com.qiaomu.modules.workflow.service.YwWorkflowMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-29 0:00
 */
@Service
public class DeleteCommunityService {

    @Autowired
    private YwCommunityService communityService;

    @Autowired
    private CarportService carportService;

    @Autowired
    private SysFileService sysFileService;

    @Resource
    private CommunityAdvertiseDao communityAdvertiseDao;

    @Resource
    private LoginStatisticsDao loginStatisticsDao;

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private UserExtendService userExtendService;

    @Autowired
    private YwWorkflowInfoService workflowInfoService;

    /**
     *  和社区关联信息表
     * yw_advertise
     * yw_carport  车辆表
     * yw_community_advertise 社区商户关联表
     * yw_community_login_statistics 社区统计表
     * yw_invitation 公告表
     * yw_user_extend 用户扩展信息表
     * yw_user_workflow 工作人员社区流程关联表
     * yw_workflow_info 社区流程表
     * yw_workflow_message  社区流程管理
     * --pluto_article  发帖表
     * --pluto_article_comment
     * --push_message_data 推送信息表
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void deleteInfo(YwCommunity community) throws InterruptedException {
        //删除社区表信息
        communityService.deleteById(community.getId());
        //删除车辆表信息
        List<CarportEntity> carportList = carportService.selectByCommunityId(community.getId());
        if(carportList.size()>0){
            carportList.forEach((m)->deleteFile(m.getImgPath()));
        }
        carportService.deleteByCommunityId(community.getId());
        //删除社区商户关联表
        communityAdvertiseDao.deleteByCommunityId(community.getId());
        //删除社区统计表
        loginStatisticsDao.deleteByCommunityId(community.getId());
        //删除公告表*****[{},{}]
        List<InvitationEntity> invitationList = invitationService.selectByCommunityId(community.getId());
        if(invitationList.size()>0){
            invitationList.forEach(n-> {
                String imgJson = n.getImgJson();
                System.out.println("community = [" + imgJson + "]");
                //deleteFile()
                List<Map<String,String>> map  = (List<Map<String,String>>)JSON.parse(imgJson);
                map.forEach(m->{
                    deleteFile(JSON.toJSONString(m));
                });
            });
        }
        invitationService.deleteByCommunity(community.getId());
        //删除用户扩展信息表
        userExtendService.deleteByCommunity(community.getId());
        //工作人员社区流程关联表
        List<YwWorkflowInfo> infoList = workflowInfoService.selectByCommunity(community.getId());
        infoList.forEach(m-> {
            Map<String,String> map  = JSONObject.parseObject(m.getPictureId(), Map.class);
           if (map.size()>0)deleteFile(m.getPictureId());
        });
        workflowInfoService.deleteByCommunity(community.getId());
    }

    private void deleteFile(String urlJson){
        Map<String,String> map = JSONObject.parseObject(urlJson, Map.class);//jsonmap转map
        map.forEach((m,n) -> sysFileService.deleteFileByHttpUrl(n));
    }
}
