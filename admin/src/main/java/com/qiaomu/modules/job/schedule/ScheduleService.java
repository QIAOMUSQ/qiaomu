package com.qiaomu.modules.job.schedule;

import com.qiaomu.common.utils.Constant;
import com.qiaomu.modules.workflow.dao.CarportDao;
import com.qiaomu.modules.workflow.entity.CarportEntity;
import com.qiaomu.modules.propertycompany.dao.AdvertiseBrowseDao;
import com.qiaomu.modules.propertycompany.entity.AdvertiseBrowse;
import com.qiaomu.modules.propertycompany.entity.LoginStatistics;
import com.qiaomu.modules.propertycompany.service.LoginStatisticsService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 李品先
 * @description: 定时从redis中读取浏览人数
 * @Date 2019-07-29 23:26
 */
@Component
public class ScheduleService {

    @Resource
    private CarportDao carportDao;

    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private AdvertiseBrowseDao advertiseBrowseDao;

    @Autowired
    private LoginStatisticsService loginStatisticsService;

    @Scheduled(cron = "3 0 0 1/1 * ?")
    public void statisticsCommunity(){
        redisTemplate.delete("browse_person");
    }

    @Scheduled(cron="0 0/2 * * * ? ")
    public void getBrowsePerson(){
        BoundHashOperations<String, String, Object> browsePersons =redisTemplate.boundHashOps("browse_person");
        browsePersons.entries().forEach((m,n) ->
                        update(m,n)
        );
    }

    private void update(String key, Object value){
        if (key.contains(Constant.REDIS_KEY_CARPORT)) {
            //车位浏览人数
            Long id = Long.valueOf(key.substring(8, key.length()));
            CarportEntity carport = carportDao.selectById(id);
            if (carport != null) {
                carport.setBrowsePerson(Long.valueOf((String) value));
                carportDao.updateById(carport);
            }
        } else if (key.contains(Constant.REDIS_KEY_ADVERTISE)) {
            //广告浏览
            Long id = Long.valueOf(key.split("_")[1]);
            AdvertiseBrowse browse = new AdvertiseBrowse(id, new Date(), Integer.valueOf((String) value));
            advertiseBrowseDao.insertOrUpdate(browse);
        }else if (key.contains(Constant.COMMUNITY_LOGIN_COUNT)){
            //社区登录人数统计
            Long id = Long.valueOf(key.split("_")[1]);
            LoginStatistics statistics = new LoginStatistics();
            statistics.setCommunityId(Long.valueOf(id));
            statistics.setDate(DateTime.now().toString("yyyy-MM-dd"));
            statistics.setNumber(Integer.valueOf((String) value));
            statistics.setType("1");
            loginStatisticsService.insert(statistics);
        }
    }

}
