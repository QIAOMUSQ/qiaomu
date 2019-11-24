package com.qiaomu.common.config.managet;

import com.alibaba.fastjson.JSON;
import com.qiaomu.modules.auth.service.AuthLoginService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import javax.servlet.http.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Map;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.apache.shiro.web.servlet.ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE;

/**
 * @author 李品先
 * @description:自定义Shiro sessionMananger
 * @Date 2019-06-29 10:33
 */
public class ShiroSessionManager extends DefaultWebSessionManager {
    private static final Logger log = LoggerFactory.getLogger(ShiroSessionManager.class);
    public ShiroSessionManager(){
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
       // 初次登陆将session放入请求头中
        String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if(id == null){
            if(WebUtils.toHttp(request).getCookies() != null){
                Cookie[] cookies = (Cookie[])WebUtils.toHttp(request).getCookies() ;
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("JSESSIONID")){
                        id = cookie.getValue();
                    }
                }
            }
        }
        String url = ((ShiroHttpServletRequest) request).getRequestURI().toString();
        System.out.println( WebUtils.toHttp(request).getRemoteAddr()+":"
                +WebUtils.toHttp(request).getRemoteHost()+"=====url:" +url+
                " ========= 参数：" +JSON.toJSONString(WebUtils.toHttp(request).getParameterMap()));
        if (StringUtils.isNotBlank(WebUtils.toHttp(request).getParameter("contentHtml"))){
            //公告html代码
            WebUtils.toHttp(request).setAttribute("content",WebUtils.toHttp(request).getParameter("contentHtml"));
        }
        try{
            if(StringUtils.isEmpty(id) && url.contains("login") ){
                //如果没有携带id参数则按照父类的方式在cookie进行获取
                Serializable sessionid = super.getSessionId(request, response);
                System.out.println("======时间:"+DateTime.now().toString("YYYY-MM-dd HH:mm:ss") +"___id："+sessionid);
                httpResponse.setHeader(AUTHORIZATION, sessionid.toString());
                return sessionid;
            }else{
                //如果请求头中有 authToken 则其值为sessionId
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,REFERENCED_SESSION_ID_SOURCE);
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID,id);
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID,Boolean.TRUE);
                // 每次读取之后 都把当前的 sessionId 放入 response 中
                httpResponse.setHeader(AUTHORIZATION, id);
                return id;
            }
        }catch (Exception e){
            //e.printStackTrace();
            return "";
        }
    }


    @Override
    protected void onStart(Session session, SessionContext context) {
        super.onStart(session, context);
        if(!WebUtils.isHttp(context)) {
            log.debug("SessionContext argument is not HTTP compatible or does not have an HTTP request/response pair. No session ID cookie will be set.");
        } else {
            HttpServletResponse response = WebUtils.getHttpResponse(context);
            response.setHeader(AUTHORIZATION, session.getId().toString());

        }
    }
}
