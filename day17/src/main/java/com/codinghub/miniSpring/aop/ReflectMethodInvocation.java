package com.codinghub.miniSpring.aop;

import java.lang.reflect.Method;

/**
 * @author 莱特0905
 * @Description: 反射方法调用类
 * @Date: 2024/10/29 19:49:47
 */
public class ReflectMethodInvocation implements MethodInvocation{
    /**
     * 代理对象
     */
    protected final Object proxy;

    /**
     * 目标代理对象
     */
    protected final Object target;

    /**
     * 执行方法
     */
    protected final Method method;

    /**
     * 参数
     */
    protected Object[] arguments;

    /**
     * 目标类
     */
    protected Class<?> targetClass;

    public ReflectMethodInvocation(Object proxy, Object target, Method method, Object[] arguments, Class<?> targetClass) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.targetClass = targetClass;
    }

    /**
     * 获取方法
     * @return 方法
     */
    @Override
    public Method getMethod() {
        return this.method;
    }

    /**
     * 获取参数
     * @return 参数
     */
    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    /**
     * 获取此时的引用
     * @return 此时的引用
     */
    @Override
    public Object getThis() {
        return this.target;
    }

    /**
     * 开始执行方法的调用
     * @return 调用结果
     * @throws Exception 异常
     */
    @Override
    public Object proceed() throws Exception {
        return this.method.invoke(this.target, this.arguments);
    }

    public final Object getProxy() {
        return this.proxy;
    }

    public void setArguments(Object... arguments) {
        this.arguments = arguments;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }
}
