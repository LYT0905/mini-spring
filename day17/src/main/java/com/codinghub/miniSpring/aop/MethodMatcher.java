package com.codinghub.miniSpring.aop;

import java.lang.reflect.Method;

/**
 * @author 莱特0905
 * @Description: 方法匹配接口
 * @Date: 2024/10/30 16:57:25
 */
public interface MethodMatcher {
    /**
     * 匹配方法
     * @param method 方法
     * @param targetClass 目标类
     * @return 匹配是否成功，成功返回true,错误返回false
     */
    boolean matches(Method method, Class<?> targetClass);
}
