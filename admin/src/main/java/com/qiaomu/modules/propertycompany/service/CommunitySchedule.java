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

    @Scheduled(cron = "3 0 0 1/1 * ?")
    public void initCommunity(){
        BoundHashOperations<String, String, Object> boundHashOps = redisTemplate.boundHashOps(Constant.COMMUNITY_LOGIN_COUNT);
        boundHashOps.entries().forEach((m,n) -> boundHashOps.delete(m));
    }
}
