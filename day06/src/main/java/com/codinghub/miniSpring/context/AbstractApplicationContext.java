package com.codinghub.miniSpring.context;

import com.codinghub.miniSpring.beans.BeansException;
import com.codinghub.miniSpring.beans.factory.config.BeanFactoryPostProcessor;
import com.codinghub.miniSpring.beans.factory.config.BeanPostProcessor;
import com.codinghub.miniSpring.beans.factory.config.ConfigurableListableBeanFactory;
import com.codinghub.miniSpring.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 莱特0905
 * @Description: 应用上下文抽象类
 * @Date: 2024/09/18 22:16:25
 */
public abstract class AbstractApplicationContext implements ApplicationContext{
    private Environment environment;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();
    private long startupDate;
    private final AtomicBoolean active = new AtomicBoolean();
    private final AtomicBoolean closed = new AtomicBoolean();
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 获取Bean实例
     * @param beanName Bean实例名字
     * @return Bean实例
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        return getBeanFactory().getBean(beanName);
    }

    /**
     * 根据Bean名字判断是否存在Bean实例
     * @param beanName Bean名字
     * @return 存在返回true,不存在返回false
     */
    @Override
    public Boolean containsBean(String beanName) {
        return getBeanFactory().containsBean(beanName);
    }

    /**
     * 根据Bean名字判断Bean对象是否是单例模式
     * @param name Bean名字
     * @return 是则返回true,否则返回false
     */
    @Override
    public boolean isSingleton(String name) {
        return getBeanFactory().isSingleton(name);
    }

    /**
     * 根据Bean名字判断Bean对象是否是原型模式
     * @param name Bean名字
     * @return 是则返回true,否则返回false
     */
    @Override
    public boolean isPrototype(String name) {
        return getBeanFactory().isPrototype(name);
    }

    /**
     * 获取Bean工厂后置处理器
     * @return Bean工厂后置处理器
     */
    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
        return this.beanFactoryPostProcessors;
    }

    /**
     * 刷新
     * @throws BeansException 自定义异常
     * @throws IllegalStateException 非法状态错误
     */
    @Override
    public void refresh() throws BeansException, IllegalStateException {
        postProcessBeanFactory(getBeanFactory());

        registerBeanPostProcessors(getBeanFactory());

        initApplicationEventPublisher();

        onRefresh();

        registerListeners();

        finishRefresh();
    }

    /**
     * 注册单例Bean
     * @param beanName Bean名字
     * @param singletonObject 单例Bean信息
     */
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        getBeanFactory().registerSingleton(beanName, singletonObject);
    }

    /**
     * 根据Bean名字获取Bean的类型
     * @param name Bean名字
     * @return Bean的类型
     */
    @Override
    public Class<?> getType(String name) {
        return getBeanFactory().getType(name);
    }

    /**
     * 获取单例Bean
     * @param beanName 单例Bean名字
     * @return 单例Bean
     */
    @Override
    public Object getSingleton(String beanName) {
        return getBeanFactory().getSingleton(beanName);
    }

    /**
     * 判断是否包含单例Bean
     * @param beanName 单例Bean名字
     * @return 存在返回true,不存在返回false
     */
    @Override
    public boolean containsSingleton(String beanName) {
        return getBeanFactory().containsSingleton(beanName);
    }

    /**
     * 获取所有单例bean名字
     * @return 所有单例bean名字
     */
    @Override
    public String[] getSingletonNames() {
        return getBeanFactory().getSingletonNames();
    }

    /**
     * 判断是否包含Bean
     * @param beanName Bean名字
     * @return 包含返回true，否则返回false
     */
    @Override
    public boolean containsBeanDefinition(String beanName) {
        return getBeanFactory().containsBeanDefinition(beanName);
    }

    /**
     * 获取Bean的数量
     * @return Bean的数量
     */
    @Override
    public int getBeanDefinitionCount() {
        return getBeanFactory().getBeanDefinitionCount();
    }

    /**
     * 获取Bean的名字集合
     * @return Bean的名字集合
     */
    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    /**
     * 通过Bean的类型获取Bean的名字
     * @param type Bean的类型
     * @return Bean的名字
     */
    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return getBeanFactory().getBeanNamesForType(type);
    }

    /**
     * 根据Bean类型获取Bean
     * @param type Bean类型
     * @return Bean
     * @param <T> 泛型
     * @throws BeansException 自定义异常
     */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    /**
     * 添加Bean后置处理器
     * @param beanPostProcessor Bean后置处理器
     */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        getBeanFactory().addBeanPostProcessor(beanPostProcessor);

    }

    /**
     * 获取Bean后置处理器数量
     * @return Bean后置处理数量
     */
    @Override
    public int getBeanPostProcessorCount() {
        return getBeanFactory().getBeanPostProcessorCount();
    }

    /**
     * 注册依赖的Bean对象
     * @param beanName Bean名字
     * @param dependentBeanName 依赖的Bean名字
     */
    @Override
    public void registerDependentBean(String beanName, String dependentBeanName) {
        getBeanFactory().registerDependentBean(beanName, dependentBeanName);
    }

    /**
     * 获取依赖的Bean
     * @param beanName Bean名字
     * @return 依赖的Bean
     */
    @Override
    public String[] getDependentBeans(String beanName) {
        return getBeanFactory().getDependentBeans(beanName);
    }

    /**
     * 获取Bean的依赖
     * @param beanName Bean名字
     * @return Bean的依赖
     */
    @Override
    public String[] getDependenciesForBean(String beanName) {
        return getBeanFactory().getDependenciesForBean(beanName);
    }

    /**
     * 获取应用名字
     * @return 应用名字
     */
    @Override
    public String getApplicationName() {
        return "";
    }

    /**
     * 获取启动日期
     * @return 启动日期
     */
    @Override
    public long getStartupDate() {
        return this.startupDate;
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * 获取环境属性
     * @return 环境属性
     */
    @Override
    public Environment getEnvironment() {
        return this.environment;
    }

    /**
     * 增加Bean工厂后置处理器
     * @param postProcessor Bean工厂后置处理器
     */
    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
    }

    /**
     * 是否活跃
     * @return 活跃返回true，否则返回false
     */
    @Override
    public boolean isActive() {
        return true;
    }

    public ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 获取Bean工厂
     * @return Bean工厂
     * @throws IllegalStateException 非法状态错误
     */
    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    /**
     * 注册监听者
     */
    abstract void registerListeners();

    /**
     * 初始化应用事件推送者
     */
    abstract void initApplicationEventPublisher();

    /**
     * Bean工厂后置处理器
     * @param bf 可配置的可列表Bean工厂
     */
    abstract void postProcessBeanFactory(ConfigurableListableBeanFactory bf);

    /**
     * 注册Bean工厂后置处理器
     * @param bf 可配置的可列表Bean工厂
     */
    abstract void registerBeanPostProcessors(ConfigurableListableBeanFactory bf);

    /**
     * 正在刷新
     */
    abstract void onRefresh();

    /**
     * 完成刷新
     */
    abstract void finishRefresh();
}
