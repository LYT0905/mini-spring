package com.codinghub.miniSpring.beans.factory.config;

import com.codinghub.miniSpring.beans.factory.BeanFactory;

/**
 * @author 莱特0905
 * @Description: 配置Bean工厂
 * @Date: 2024/09/18 20:21:33
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {
    /**
     * 单例模式
     */
    String SCOPE_SINGLETON = "singleton";

    /**
     * 原型模式
     */
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加Bean后置处理器
     * @param beanPostProcessor Bean后置处理器
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 获取Bean后置处理器数量
     * @return Bean后置处理数量
     */
    int getBeanPostProcessorCount();

    /**
     * 注册依赖的Bean对象
     * @param beanName Bean名字
     * @param dependentBeanName 依赖的Bean名字
     */
    void registerDependentBean(String beanName, String dependentBeanName);

    /**
     * 获取依赖的Bean
     * @param beanName Bean名字
     * @return 依赖的Bean
     */
    String[] getDependentBeans(String beanName);

    /**
     * 获取Bean的依赖
     * @param beanName Bean名字
     * @return Bean的依赖
     */
    String[] getDependenciesForBean(String beanName);
}
