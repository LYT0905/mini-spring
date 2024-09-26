package com.codinghub.miniSpring.web;

import com.codinghub.miniSpring.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author 莱特0905
 * @Description: Web应用上下文
 * @Date: 2024/09/25 19:04:16
 */
public interface WebApplicationContext extends ApplicationContext {
    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    /**
     * 获取程序上下文事件
     * @return 程序上下文事件
     */
    ServletContext getServletContext();

    /**
     * 设置程序上下文事件
     * @param servletContext 程序上下文事件
     */
    void setServletContext(ServletContext servletContext);
}
