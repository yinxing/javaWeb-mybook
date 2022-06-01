package com.example.mybook.listener;

import com.example.mybook.action.TypeServlet;
import com.example.mybook.bean.Type;
import com.example.mybook.biz.TypeBiz;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class TypeServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 获取数据库所有信息
        TypeBiz biz = new TypeBiz();
        List<Type> types= biz.getAll();
        // applicaion 获取
        ServletContext application = sce.getServletContext();
        // 将信息存入application;
        application.setAttribute("types",types);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
