package com.qiaomu.common.listener;

import com.qiaomu.common.utils.Constant;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 李品先
 * @description:用户请求信息监听器
 * @Date 2019-05-05 22:19
 */
@WebListener
public class ListenerServletRequest implements ServletRequestListener {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void requestDestroyed(ServletRequestEvent requestEvent) {

    }

    /**
     * 主要监听用户登录次数
     * @param servletRequestEvent
     */
    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest)servletRequestEvent.getServletRequest();
        String url = request.getRequestURL().toString();//根据url进行判断处理
        //获取社区广告
        if(url.contains("getAdvertiseByCommunity")){
            Map<String,String[]> params =  WebUtils.toHttp(request).getParameterMap();
            if (params.get("communityId") != null && StringUtils.isNotBlank(params.get("communityId")[0])) {
                String communityId =params.get("communityId")[0];
                String number = (String) redisTemplate.boundHashOps("browse_person").get(Constant.COMMUNITY_LOGIN_COUNT+communityId);
                if (StringUtils.isNotBlank(number)){
                    redisTemplate.boundHashOps("browse_person").put(Constant.COMMUNITY_LOGIN_COUNT+communityId,String.valueOf(Integer.valueOf(number)+1));
                }else {
                    redisTemplate.boundHashOps("browse_person").put(Constant.COMMUNITY_LOGIN_COUNT+communityId,String.valueOf(1));
                }
            }

        }else if(url.contains("getAdvertiseById")){
            //监听广告浏览人数
            Map<String,String[]> params =  WebUtils.toHttp(request).getParameterMap();
            if (params.get("id") != null && StringUtils.isNotBlank(params.get("id")[0])) {
                String id = params.get("id")[0];
                String number = (String) redisTemplate.boundHashOps("browse_person").get(Constant.REDIS_KEY_ADVERTISE+id);
                if (StringUtils.isNotBlank(number)){
                    redisTemplate.boundHashOps("browse_person").put(Constant.REDIS_KEY_ADVERTISE+id,String.valueOf(Integer.valueOf(number)+1));
                }else {
                    redisTemplate.boundHashOps("browse_person").put(Constant.REDIS_KEY_ADVERTISE+id,String.valueOf(1));
                }

            }
        }
    }
}
