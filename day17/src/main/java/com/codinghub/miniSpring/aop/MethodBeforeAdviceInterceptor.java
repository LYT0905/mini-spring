package com.codinghub.miniSpring.aop;

/**
 * @author 莱特0905
 * @Description: 建议拦截器之前的方法
 * @Date: 2024/10/29 19:56:45
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor{
    /**
     * 在执行业务代码之前执行的动作
     */
    private final MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
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
        this.advice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }
}
