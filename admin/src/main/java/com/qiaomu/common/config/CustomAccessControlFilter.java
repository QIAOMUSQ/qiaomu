package com.qiaomu.common.config;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李品先
 * @description: 自定义拦截器
 * @Date 2019-05-30 23:13
 */

public class CustomAccessControlFilter extends AccessControlFilter {

    /**
     * 表示是否允许访问
     * @param servletRequest
     * @param servletResponse
     * @param o
     * @return
     * @throws Exception
     */
   @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        String url = ((ShiroHttpServletResponse) servletResponse).getRequest().getRequestURI().toString();
        if(url.contains("/mobile/")){
            //当没有登录时候
            if(url.contains("login")|| url.contains("register")){
                return true;
            }else if(!SecurityUtils.getSubject().isAuthenticated()){
                return false;
            }
            return true;
        }
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            WebUtils.issueRedirect(servletRequest, servletResponse, url);
            return false;
        }
        return true;
    }


    /**
     * 表示当访问拒绝时是否已经处理了
     * 访问拒绝才会进来此方法
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            saveRequestAndRedirectToLogin(servletRequest, servletResponse);
            return false;
        }
        return true;
    }

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        String loginUrl = getLoginUrl();

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("text/html; charset=utf-8");
        httpRequest.setCharacterEncoding("UTF-8");
        Map<String,Object> map = new HashMap<String,Object>();
        String url = ((ShiroHttpServletRequest) request).getRequestURI().toString();
        // 是否为APP登录请求
        if (url.contains("/mobile/")) {
            //当没有登录时候
            map.put("error","请登录");
            ResponseEntity result = new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);

            httpResponse.getWriter().append(JSON.toJSONString(result));
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        } else {
            // PC跳转 如果是非Ajax请求 按默认的配置跳转到登录页面
            if (!"XMLHttpRequest".equalsIgnoreCase(httpRequest
                    .getHeader("X-Requested-With"))) {// 不是ajax请求
                WebUtils.issueRedirect(request, response, loginUrl);
            } else {
                // 如果是Aajx请求,则返回会话失效的JSON信息
                map.put("error","请求失败!");
                ResponseEntity result = new ResponseEntity<>(map, HttpStatus.OK);
                httpResponse.getWriter().append(JSON.toJSONString(result));
                httpResponse.getWriter().flush();
                httpResponse.getWriter().close();
            }
        }
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
/*
        httpServletResponse.addHeader(
                "Access-Control-Allow-Headers",
                "Content-Type,X-Requested-With," +
                        "accept,Origin," +
                        "Access-Control-Request-Method," +
                        "Access-Control-Request-Headers," +
                        "token,Authorization");*/
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
