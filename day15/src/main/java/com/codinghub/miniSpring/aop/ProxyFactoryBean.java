package com.codinghub.miniSpring.aop;

import com.codinghub.miniSpring.beans.factory.FactoryBean;
import com.codinghub.miniSpring.utils.ClassUtils;

/**
 * @author 莱特0905
 * @Description: 代理工厂Bean
 * @Date: 2024/10/25 16:52:29
 */
public class ProxyFactoryBean implements FactoryBean<Object> {
    /**
     * AOP代理工厂
     */
    private AopProxyFactory aopProxyFactory;

    /**
     * 拦截器名称
     */
    private String[] interceptorNames;

    /**
     * 目标名字
     */
    private String targetName;

    /**
     * 目标对象
     */
    private Object target;

    /**
     * 实例
     */
    private Object object;

    /**
     * 代理类加载器
     */
    private ClassLoader proxyClassLoader = ClassUtils.getDefaultClassLoader();

    /**
     * 单例实例
     */
    private Object singletonInstance;

    public ProxyFactoryBean() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    /**
     * 获取Bean实例
     * @return 实例
     * @throws Exception 异常
     */
    @Override
    public Object getObject() throws Exception {
        return getSingletonInstance();
    }

    /**
     * 获取实例类型
     * @return 实例Class字节对象
     */
    @Override
    public Class<?> getObjectType() {
        return null;
    }

    /**
     * 获取单例实例
     * @return 单例实例
     */
    private synchronized Object getSingletonInstance(){
        if(this.singletonInstance == null){
            this.singletonInstance = getProxy(createAopProxy());
            System.out.println("----------return proxy for :"+this.singletonInstance+"--------"+this.singletonInstance.getClass());
        }
        return this.singletonInstance;
    }

    /**
     * 获取代理对象
     * @param aopProxy Aop代理
     * @return 代理对象
     */
    protected Object getProxy(AopProxy aopProxy){
        return aopProxy.getProxy();
    }

    /**
     * 创建代理对象
     * @return AOP代理对象
     */
    protected AopProxy createAopProxy(){
        System.out.println("----------createAopProxy for :"+ target +"--------");
        return getAopProxyFactory().createAopProxy(target);
    }

    public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }
    public AopProxyFactory getAopProxyFactory() {
        return this.aopProxyFactory;
    }
    public void setInterceptorNames(String... interceptorNames) {
        this.interceptorNames = interceptorNames;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
    public Object getTarget() {
        return target;
    }
    public void setTarget(Object target) {
        this.target = target;
    }

}
