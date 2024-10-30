package com.codinghub.miniSpring.aop;

import java.lang.reflect.Method;

/**
 * @author 莱特0905
 * @Description: 业务执行完需要执行的动作
 * @Date: 2024/10/29 19:59:39
 */
public interface AfterReturningAdvice extends AfterAdvice{
    /**
     * 业务执行完需要执行的动作
     * @param returnValue 返回值
     * @param method 方法
     * @param args 参数
     * @param target 目标对象
     * @throws Exception 异常
     */
    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Exception;
}
