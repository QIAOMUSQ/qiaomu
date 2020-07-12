package com.qiaomu.modules.article.schedule;

import com.qiaomu.modules.article.dao.ArticleStatisticDao;
import com.qiaomu.modules.article.entity.ArticleStatisticEntity;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 李品先
 * @description: 用户发帖统计
 * @Date 2020-07-05 16:30
 */
@Component
public class ArticleStatistic {
    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    private SysUserService sysUserService;
    @Resource
    private ArticleStatisticDao articleStatisticDao;

    public static final String category = "article:category:communityId_*";

    @Scheduled(cron="0 0 0 1/1 * ?")
    public void statisticArticle(){
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        Set<String> keys = stringRedisTemplate.keys(category);
        for(String key : keys){
            System.out.println(key);
            Long userId= Long.valueOf(key.substring(key.lastIndexOf(":")+1));
            SysUserEntity user =  sysUserService.queryById(userId);
            Long communityId = Long.valueOf(key.substring(key.lastIndexOf("_")+1,key.lastIndexOf(":")));
            Integer data = Integer.valueOf(opsForValue.get(key));
            ArticleStatisticEntity statisticEntity = new ArticleStatisticEntity(String.valueOf(userId),new Date(),user.getRealName(),data,communityId);
            articleStatisticDao.insert(statisticEntity);
            stringRedisTemplate.delete(key);
        }
    }

}
