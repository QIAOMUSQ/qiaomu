package com.qiaomu.common.config;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

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
            return false;
        }
        return true;
    }


}
