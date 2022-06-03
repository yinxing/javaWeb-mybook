package com.example.mybook.action;

import com.example.mybook.bean.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //向下转型
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;

        //获得请求uri
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        //判断请求是否为登录请求  是则放行
        if("/mybook_war_exploded/code.let".equals(requestURI)||"/mybook_war_exploded/login.html".equals(requestURI) || "/mybook_war_exploded/user.let".equals(requestURI)){
            filterChain.doFilter(request,response);
            return;
        }
        //获得session域中username
        Object user = request.getSession().getAttribute("user");
        //判断是否有用户名 没有就跳回登录页面
        if(user==null){
            response.sendRedirect("login.html");
            return;
        }
        //放行
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {}
}
