package com.codinghub.miniSpring.aop;

/**
 * @author 莱特0905
 * @Description: 业务执行完需要执行的动作拦截器
 * @Date: 2024/10/29 20:01:32
 */
public class AfterReturningAdviceInterceptor implements MethodInterceptor{
    /**
     * 业务执行完需要执行的动作
     */
    private final AfterReturningAdvice advice;

    public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
        this.advice = advice;
    }

    /**
     * 执行方法
     * @param invocation 方法调用
     * @return 执行结果
     * @throws Exception 异常
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Exception {
        Object returnValue = invocation.proceed();
        this.advice.afterReturning(returnValue, invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return returnValue;
    }
}
