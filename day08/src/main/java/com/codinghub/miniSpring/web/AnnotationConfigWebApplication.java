package com.codinghub.miniSpring.web;

import com.codinghub.miniSpring.context.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author 莱特0905
 * @Description: 注解配置的应用程序
 * @Date: 2024/09/25 19:16:43
 */
public class AnnotationConfigWebApplication extends ClassPathXmlApplicationContext implements WebApplicationContext {

    private ServletContext servletContext;

    public AnnotationConfigWebApplication(String fileName) {
        super(fileName);
    }

    /**
     * 获取程序上下文事件
     * @return 程序上下文事件
     */
    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    /**
     * 设置程序上下文事件
     * @param servletContext 程序上下文事件
     */
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
