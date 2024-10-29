package com.codinghub.miniSpring.aop;

/**
 * @author 莱特0905
 * @Description: 默认AOP代理工厂实现类
 * @Date: 2024/10/25 16:49:05
 */
public class DefaultAopProxyFactory implements AopProxyFactory{
    /**
     * 创建代理对象
     * @param target 目标代理对象
     * @return AOP代理对象
     */
    @Override
    public AopProxy createAopProxy(Object target) {
        return new JdkDynamicAopProxy(target);
    }
}
