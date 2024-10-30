package com.codinghub.miniSpring.aop;

import java.lang.reflect.Method;

/**
 * @author 莱特0905
 * @Description: 方法调用
 * @Date: 2024/10/29 19:29:15
 */
public interface MethodInvocation {
    /**
     * 获取方法
     * @return 方法
     */
    Method getMethod();

    /**
     * 获取参数
     * @return 参数
     */
    Object[] getArguments();

    /**
     * 获取此时的引用
     * @return 此时的引用
     */
    Object getThis();

    /**
     * 开始执行方法的调用
     * @return 调用结果
     * @throws Exception 异常
     */
    Object proceed() throws Exception;
}
