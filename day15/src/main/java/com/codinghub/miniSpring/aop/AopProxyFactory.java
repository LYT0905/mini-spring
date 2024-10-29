package com.codinghub.miniSpring.aop;

/**
 * @author 莱特0905
 * @Description: AOP代理工厂
 * @Date: 2024/10/25 16:48:07
 */
public interface AopProxyFactory {
    /**
     * 创建代理对象
     * @param target 目标代理对象
     * @return AOP代理对象
     */
    AopProxy createAopProxy(Object target);
}
