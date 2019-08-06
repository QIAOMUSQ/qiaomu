package com.qiaomu.common.servlet;

import com.qiaomu.modules.sys.service.UserExtendService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 李品先
 * @description:用户请求信息监听器
 * @Date 2019-05-05 22:19
 */
@WebListener
public class MyServletRequest implements ServletRequestListener {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserExtendService userExtendService;


    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest)servletRequestEvent.getServletRequest();
        String url = request.getRequestURL().toString();//根据url进行判断处理
        if(url.contains("login")){
            //String params =  request.get;
        }
        System.out.println(url);
    }
}
