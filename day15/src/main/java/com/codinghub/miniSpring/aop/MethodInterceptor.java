package com.codinghub.miniSpring.aop;

/**
 * @author 莱特0905
 * @Description: 方法拦截器
 * @Date: 2024/10/29 19:28:26
 */
public interface MethodInterceptor extends Interceptor{
    /**
     * 执行方法
     * @param invocation 方法调用
     * @return 执行结果
     * @throws Exception 异常
     */
    Object invoke(MethodInvocation invocation) throws Exception;
}
