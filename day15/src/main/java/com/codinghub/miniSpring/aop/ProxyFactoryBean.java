package com.codinghub.miniSpring.aop;

import com.codinghub.miniSpring.beans.BeansException;
import com.codinghub.miniSpring.beans.factory.BeanFactory;
import com.codinghub.miniSpring.beans.factory.BeanFactoryAware;
import com.codinghub.miniSpring.beans.factory.FactoryBean;
import com.codinghub.miniSpring.utils.ClassUtils;

/**
 * @author 莱特0905
 * @Description: 代理工厂Bean
 * @Date: 2024/10/25 16:52:29
 */
public class ProxyFactoryBean implements FactoryBean<Object>, BeanFactoryAware {
    /**
     * Bean工厂
     */
    private BeanFactory beanFactory;

    /**
     * AOP代理工厂
     */
    private AopProxyFactory aopProxyFactory;

    /**
     * 拦截器名称
     */
    private String interceptorName;

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

    /**
     * 拦截器代理
     */
    private Advisor advisor;

    public ProxyFactoryBean() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 获取Bean实例
     * @return 实例
     * @throws Exception 异常
     */
    @Override
    public Object getObject() throws Exception {
        initializeAdvisor();
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
     * 初始化拦截器代理
     */
    private synchronized void initializeAdvisor(){
        Object advice = null;
        MethodInterceptor mi = null;
        try {
            advice = this.beanFactory.getBean(this.interceptorName);
        }catch (BeansException ex){
            ex.printStackTrace();
        }
        if (advice instanceof BeforeAdvice){
            mi = new MethodBeforeAdviceInterceptor((MethodBeforeAdvice)advice);
        } else if (advice instanceof AfterAdvice) {
            mi = new AfterReturningAdviceInterceptor((AfterReturningAdvice)advice);
        } else if (advice instanceof MethodInterceptor) {
            mi = (MethodInterceptor) advice;
        }
        advisor = new DefaultAdvisor();
        advisor.setMethodInterceptor(mi);
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
        return getAopProxyFactory().createAopProxy(target, this.advisor);
    }

    public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }
    public AopProxyFactory getAopProxyFactory() {
        return this.aopProxyFactory;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
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
