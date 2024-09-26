package com.codinghub.miniSpring.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author 莱特0905
 * @Description: 上下文装载监听器
 * @Date: 2024/09/25 18:59:57
 */
public class ContextLoaderListener implements ServletContextListener {
    public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";
    private WebApplicationContext context;

    public ContextLoaderListener() {
    }

    public ContextLoaderListener(WebApplicationContext context){
        this.context = context;
    }

    /**
     * 上下文初始化时会执行
     * @param servletContextEvent 程序上下文事件
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initWebApplicationContext(servletContextEvent.getServletContext());
    }

    /**
     * 上下文销毁时执行
     * @param servletContextEvent 程序上下文事件
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    /**
     * 初始化Web应用程序上下文事件
     * @param servletContext 程序上下文事件
     */
    private void initWebApplicationContext(ServletContext servletContext){
        String sContextLocation = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
        System.out.println("sContextLocation-----------" + sContextLocation);
        WebApplicationContext wac = new AnnotationConfigWebApplication(sContextLocation);
        wac.setServletContext(servletContext);
        this.context = wac;
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
    }
}
