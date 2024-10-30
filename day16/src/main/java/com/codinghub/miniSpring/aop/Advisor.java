package com.codinghub.miniSpring.aop;

/**
 * @author 莱特0905
 * @Description: 拦截器代理
 * @Date: 2024/10/29 19:31:33
 */
public interface Advisor {
    /**
     * 获取方法拦截器
     * @return 方法拦截器
     */
    MethodInterceptor getMethodInterceptor();

    /**
     * 设置方法拦截器
     * @param methodInterceptor 方法拦截器
     */
    void setMethodInterceptor(MethodInterceptor methodInterceptor);
}
