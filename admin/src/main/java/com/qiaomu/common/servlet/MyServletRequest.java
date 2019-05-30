package com.qiaomu.common.servlet;

import com.qiaomu.modules.sys.entity.YwUserExtend;
import com.qiaomu.modules.sys.service.YwUserExtendService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    private YwUserExtendService userExtendService;


    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest)servletRequestEvent.getServletRequest();
        String url  = request.getRequestURI().toString();
        if(url.contains("/App/")){
            Session session = (Session) request.getSession();

        }


       /*

        String name = request.getParameter("phone");
        if(name != null && !"".equals(name)){
            YwUserExtend userExtend = userExtendService.getUserExtend(name);
            //request.setAttribute("");
        }

        String url = request.getRequestURL().toString();//根据url进行判断处理
        System.out.println(url);*/
    }
}
