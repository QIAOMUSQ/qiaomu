package com.qiaomu.modules.propertycompany.service;

import com.qiaomu.common.utils.Constant;
import com.qiaomu.modules.propertycompany.entity.LoginStatistics;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 李品先
 * @description:关于社区统计数
 * @Date 2019-08-24 11:50
 */
@Component
public class CommunitySchedule {

    @Autowired
    private LoginStatisticsService loginStatisticsService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Scheduled(cron = "0 0/5 * * * ? ")
    public void statisticsCommunity(){
        BoundHashOperations<String, String, Object> boundHashOps = redisTemplate.boundHashOps(Constant.COMMUNITY_LOGIN_COUNT);
        boundHashOps.entries().forEach((m,n) -> insertTable((String) m,(String)n));
    }

    public void insertTable(String community,String value){
        LoginStatistics statistics = new LoginStatistics();
        statistics.setCommunityId(Long.valueOf(community));
        statistics.setDate(DateTime.now().toString("yyyy-MM-dd"));
        statistics.setNumber(Integer.valueOf(value));
        statistics.setType("1");
        loginStatisticsService.insert(statistics);
    }

    /**
     * 每天凌晨清除redis数据
     */
    @Scheduled(cron = "3 0 0 1/1 * ?")
    public void initCommunity(){
        BoundHashOperations<String, String, Object> boundHashOps = redisTemplate.boundHashOps(Constant.COMMUNITY_LOGIN_COUNT);
        boundHashOps.entries().forEach((m,n) -> boundHashOps.delete(m));
    }

    /**
     * 定时清除community中已经删除数据
     * 和社区关联信息表
     * yw_advertise
     * yw_carport
     * yw_community_advertise
     * yw_community_login_statistics
     * yw_invitation
     * yw_user_extend
     * yw_user_workflow
     * yw_workflow_info
     * yw_workflow_message
     * pluto_article
     * pluto_article_comment
     * push_message_data
     */
    public void deleteCommunity(){

    }
}
