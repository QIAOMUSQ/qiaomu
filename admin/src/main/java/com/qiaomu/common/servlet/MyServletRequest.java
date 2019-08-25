package com.qiaomu.common.servlet;

import com.qiaomu.common.utils.Constant;
import com.qiaomu.modules.propertycompany.service.impl.YwCommunityServiceImpl;
import com.qiaomu.modules.sys.service.UserExtendService;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 李品先
 * @description:用户请求信息监听器
 * @Date 2019-05-05 22:19
 */
@WebListener
public class MyServletRequest implements ServletRequestListener {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private YwCommunityServiceImpl communityService;


    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    /**
     * 主要监听用户登录次数
     * @param servletRequestEvent
     */
    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest)servletRequestEvent.getServletRequest();
        String url = request.getRequestURL().toString();//根据url进行判断处理
        if(url.contains("getAdvertiseByCommunity")){
            Map<String,String[]> params =  WebUtils.toHttp(request).getParameterMap();
            String communityId =params.get("communityId")[0];
            BoundHashOperations<String, String, Object> boundHashOps = redisTemplate.boundHashOps(Constant.COMMUNITY_LOGIN_COUNT);
            boundHashOps.increment(communityId,1);
        }
    }
}
