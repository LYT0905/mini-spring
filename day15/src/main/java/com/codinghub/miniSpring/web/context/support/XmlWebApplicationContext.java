package com.codinghub.miniSpring.web.context.support;

import com.codinghub.miniSpring.context.ClassPathXmlApplicationContext;
import com.codinghub.miniSpring.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author 莱特0905
 * @Description: Xml配置Web应用上下文
 * @Date: 2024/09/26 17:31:00
 */
public class XmlWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {
    private ServletContext servletContext;

    public XmlWebApplicationContext(String fileName){
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
