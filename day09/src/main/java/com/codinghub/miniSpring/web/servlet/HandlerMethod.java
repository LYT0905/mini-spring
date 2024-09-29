package com.codinghub.miniSpring.web.servlet;

import java.lang.reflect.Method;

/**
 * @author 莱特0905
 * @Description: 处理方法
 * @Date: 2024/09/26 17:36:45
 */
public class HandlerMethod {
    /**
     * 实例Bean
     */
    private Object bean;

    /**
     * 实例Bean类型
     */
    private Class<?> beanType;

    /**
     * 方法
     */
    private Method method;

    /**
     * 方法参数
     */
    private MethodParameter[] parameters;

    /**
     * 返回类型
     */
    private Class<?> returnType;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    public HandlerMethod(Method method, Object obj){
        this.setMethod(method);
        this.setBean(obj);
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
