package com.codinghub.miniSpring.aop.framework.autoproxy;

import com.codinghub.miniSpring.aop.*;
import com.codinghub.miniSpring.beans.BeansException;
import com.codinghub.miniSpring.beans.factory.BeanFactory;
import com.codinghub.miniSpring.beans.factory.config.BeanPostProcessor;
import com.codinghub.miniSpring.utils.PatternMatchUtils;

/**
 * @author 莱特0905
 * @Description: 根据Bean名字自动生成代理
 * @Date: 2024/10/30 18:03:01
 */
public class BeanNameAutoProxyCreator implements BeanPostProcessor {
    /**
     * 匹配模式
     */
    String pattern;

    /**
     * Bean工厂
     */
    private BeanFactory beanFactory;

    /**
     * AOP代理工厂
     */
    private AopProxyFactory aopProxyFactory;

    /**
     * 拦截器名字
     */
    private String interceptorName;

    /**
     * 切点代理
     */
    private PointcutAdvisor advisor;

    public BeanNameAutoProxyCreator() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    @Override
    public Object postProcessorBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(isMatch(beanName, this.pattern)){
            ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
            proxyFactoryBean.setTarget(bean);
            proxyFactoryBean.setBeanFactory(beanFactory);
            proxyFactoryBean.setAopProxyFactory(aopProxyFactory);
            proxyFactoryBean.setInterceptorName(interceptorName);
            bean = proxyFactoryBean;
            return proxyFactoryBean;
        }
        return bean;
    }

    @Override
    public Object postProcessorAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    protected AopProxy createAopProxy(Object target){
        return this.aopProxyFactory.createAopProxy(target, this.advisor);
    }

    protected Object getProxy(AopProxy aopProxy){
        return aopProxy.getProxy();
    }

    /**
     * 是否成功匹配上
     * @param beanName Bean名字
     * @param mappedName 需要映射的方法名字
     * @return 匹配上返回true,否则返回false
     */
    protected boolean isMatch(String beanName, String mappedName){
        return PatternMatchUtils.simpleMatch(mappedName, beanName);
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setAdvisor(PointcutAdvisor advisor) {
        this.advisor = advisor;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }

}
