package com.codinghub.miniSpring.context;

import com.codinghub.miniSpring.beans.BeansException;
import com.codinghub.miniSpring.beans.factory.ListableBeanFactory;
import com.codinghub.miniSpring.beans.factory.config.BeanFactoryPostProcessor;
import com.codinghub.miniSpring.beans.factory.config.ConfigurableBeanFactory;
import com.codinghub.miniSpring.beans.factory.config.ConfigurableListableBeanFactory;
import com.codinghub.miniSpring.core.env.Environment;
import com.codinghub.miniSpring.core.env.EnvironmentCapable;

/**
 * @author 莱特0905
 * @Description: 应用上下文
 * @Date: 2024/09/18 21:24:28
 */
public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher {
    /**
     * 获取应用名字
     * @return 应用名字
     */
    String getApplicationName();

    /**
     * 获取启动日期
     * @return 启动日期
     */
    long getStartupDate();

    /**
     * 获取Bean工厂
     * @return Bean工厂
     * @throws IllegalStateException 非法状态错误
     */
    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    /**
     * 设置环境属性
     * @param environment 环境属性
     */
    void setEnvironment(Environment environment);

    /**
     * 获取环境属性
     * @return 环境属性
     */
    Environment getEnvironment();

    /**
     * 增加Bean工厂后置处理器
     * @param postProcessor Bean工厂后置处理器
     */
    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    /**
     * 刷新
     * @throws BeansException 自定义异常
     * @throws IllegalStateException 非法状态错误
     */
    void refresh() throws BeansException, IllegalStateException;

    /**
     * 关闭
     */
    void close();

    /**
     * 是否活跃
     * @return 活跃返回true，否则返回false
     */
    boolean isActive();

}
