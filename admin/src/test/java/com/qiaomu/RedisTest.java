package com.qiaomu;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.RedisUtils;
import com.qiaomu.modules.infopublish.entity.PushMessage;
import com.qiaomu.modules.infopublish.service.PushRedisMessageService;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private PushRedisMessageService redisMessageService;
    @Test
    public void contextLoads() {
        SysUserEntity user = new SysUserEntity();
        user.setEmail("123456@qq.com");
        redisUtils.set("user", user);

        System.out.println(ToStringBuilder.reflectionToString(redisUtils.get("user", SysUserEntity.class)));
    }
    @Test
    public void testPushRedis(){

      for (int i=0;i<10;i++){
          PushMessage message = new PushMessage();
          message.setMessage("测试"+i+"");
          message.setReceivePhone("15157150200");
          message.setType(i+"");
          message.setTime(DateTime.now().toString("YYYY-MM-dd HH:mm:ss"));
          message.setCommunityId(1l);
          //redisTemplate.convertAndSend("message", JSON.toJSONString(message));
          redisTemplate.boundHashOps("message_history").put("history_"+1515715020+i,JSON.toJSONString(message));
         /*Object obj =  redisTemplate.opsForList().leftPop("message_history");
          System.out.println(obj);*/
      }

    }

    @Test
    public void testPushMessage(){
        PushMessage message = new PushMessage();
        message.setMessage("测试222");
        message.setReceivePhone("15157150200");
        message.setType("0");
        message.setTime(DateTime.now().toString("YYYY-MM-dd HH:mm:ss"));
        message.setCommunityId(1l);
        message.setPushPhone("15157150201");
        redisMessageService.pushMessageToRedis(message);
        Object object = redisTemplate.boundHashOps("message_history").get("history_"+message.getReceivePhone()+"_"+message.getTime().replace(" ","").replace("-","").replace(":",""));
        System.out.println(message);
    }


}
