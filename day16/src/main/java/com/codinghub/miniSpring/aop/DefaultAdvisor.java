package com.codinghub.miniSpring.aop;

/**
 * @author 莱特0905
 * @Description: 默认Advisor执行类
 * @Date: 2024/10/29 19:43:47
 */
public class DefaultAdvisor implements Advisor{
    /**
     * 方法拦截器
     */
    private MethodInterceptor methodInterceptor;

    public DefaultAdvisor() {
    }

    /**
     * 获取方法拦截器
     * @return 方法拦截器
     */
    @Override
    public MethodInterceptor getMethodInterceptor() {
        return this.methodInterceptor;
    }

    /**
     * 设置方法拦截器
     * @param methodInterceptor 方法拦截器
     */
    @Override
    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    @Override
    public Advice getAdvice() {
        return this.methodInterceptor;
    }


}
