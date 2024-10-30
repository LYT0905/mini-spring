package com.codinghub.miniSpring.aop;

/**
 * @author 莱特0905
 * @Description: 根据名字匹配切点代理执行
 * @Date: 2024/10/30 17:04:54
 */
public class NameMatchMethodPointcutAdvisor implements PointcutAdvisor{

    /**
     * 拦截通知
     */
    private Advice advice = null;

    /**
     * 方法拦截器
     */
    private MethodInterceptor methodInterceptor;

    /**
     * 映射的名字
     */
    private String mappedName;

    /**
     * 根据名字匹配切点
     */
    private final NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();

    public NameMatchMethodPointcutAdvisor() {
    }

    public NameMatchMethodPointcutAdvisor(Advice advice) {
        this.advice = advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;

        MethodInterceptor mi = null;
        if(advice instanceof BeforeAdvice){
            mi = new MethodBeforeAdviceInterceptor((MethodBeforeAdvice) advice);
        } else if (advice instanceof AfterAdvice) {
            mi = new AfterReturningAdviceInterceptor((AfterReturningAdvice) advice);
        } else if (advice instanceof MethodInterceptor) {
            mi = (MethodInterceptor) advice;
        }

        setMethodInterceptor(mi);
    }

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
        this.pointcut.setMappedName(this.mappedName);
    }

    @Override
    public MethodInterceptor getMethodInterceptor() {
        return this.methodInterceptor;
    }

    @Override
    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public Pointcut getPointCut() {
        return this.pointcut;
    }
}
