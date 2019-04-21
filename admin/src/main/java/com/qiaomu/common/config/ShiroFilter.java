package com.qiaomu.common.config;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

/**
 * @author 李品先
 * @description:拦截器返回信息设置
 * @Date 2018-10-30 11:29
 */
public class ShiroFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        //判断请求是否是登录请求
        if(this.isLoginRequest(request, response)) {
            //判断请求是否是post方法
            if(this.isLoginSubmission(request, response)) {
                //执行登录验证
                return this.executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            String header = ((HttpServletRequest) request).getHeader("Content-Type");
            if(header != null && header.equals("application/x-www-form-urlencoded")){
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print("{\"isOver\" : true }");
                out.flush();
                out.close();
            }else{
                //如果访问的是非登录页面，则跳转到登录页面
                this.saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }


}
