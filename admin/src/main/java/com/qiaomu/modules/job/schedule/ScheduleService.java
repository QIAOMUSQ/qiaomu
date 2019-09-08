package com.qiaomu.modules.job.schedule;

import com.qiaomu.modules.infopublish.dao.CarportDao;
import com.qiaomu.modules.infopublish.entity.CarportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

    @Scheduled(cron="0 0/10 * * * ? ")
    public void getBrowsePerson(){
        BoundHashOperations<String, String, Object> browsePersons =redisTemplate.boundHashOps("browse_person");
        browsePersons.entries().forEach((m,n) ->
                        update(m,n)
        );
    }

    private void update(String key, Object value){
        System.out.println("获取map键值对:" + key + "-" + value);
        Long id = Long.valueOf(key.substring(8,key.length()));
        CarportEntity carport = carportDao.selectById(id);
        if(carport!=null){
            carport.setBrowsePerson(Long.valueOf((String) value));
            carportDao.updateById(carport);
        }

    }
}
