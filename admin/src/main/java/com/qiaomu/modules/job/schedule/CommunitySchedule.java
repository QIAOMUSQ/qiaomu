package com.qiaomu.modules.job.schedule;

import com.qiaomu.common.utils.Constant;
import com.qiaomu.modules.propertycompany.entity.LoginStatistics;
import com.qiaomu.modules.propertycompany.service.LoginStatisticsService;
import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import com.qiaomu.modules.propertycompany.service.impl.DeleteCommunityService;
import com.qiaomu.modules.sys.entity.YwCommunity;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

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
    @Autowired
    private YwCommunityService communityService;

    @Autowired
    private DeleteCommunityService deleteCommunityService;



    /**
     * 定时清除community中已经删除数据
     */
    @PostConstruct
    @Scheduled(cron = "2 0 0 1/1 * ?")
    public void deleteCommunity(){
        YwCommunity community = new YwCommunity();
        community.setDeleteTime(DateTime.now().plusDays(-3).toString("yyyy-MM-dd HH:mm:ss"));
        List<YwCommunity> communities = communityService.getDeleteCommunity(community);
        try{
            for (YwCommunity community1: communities){
                deleteCommunityService.deleteInfo(community1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
