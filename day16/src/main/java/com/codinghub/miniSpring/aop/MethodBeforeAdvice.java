package com.codinghub.miniSpring.aop;

import java.lang.reflect.Method;

/**
 * @author 莱特0905
 * @Description: 在执行业务代码之前执行的动作
 * @Date: 2024/10/29 19:55:16
 */
public interface MethodBeforeAdvice extends BeforeAdvice{
    /**
     * 方法执行前需要执行的动作
     * @param method 方法
     * @param args 参数
     * @param target 目标对象
     * @throws Exception 异常
     */
    void before(Method method, Object[] args, Object target) throws Exception;
}
