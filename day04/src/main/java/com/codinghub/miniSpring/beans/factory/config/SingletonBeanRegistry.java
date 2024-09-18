package com.codinghub.miniSpring.beans.factory.config;

/**
 * @author 莱特0905
 * @Description: 单例Bean注册接口
 * @Date: 2024/09/11 18:25:56
 */
public interface SingletonBeanRegistry {
    /**
     * 注册单例Bean
     * @param beanName Bean名字
     * @param singletonObject 单例Bean信息
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 获取单例Bean
     * @param beanName 单例Bean名字
     * @return 单例Bean
     */
    Object getSingleton(String beanName);

    /**
     * 判断是否包含单例Bean
     * @param beanName 单例Bean名字
     * @return 存在返回true,不存在返回false
     */
    boolean containsSingleton(String beanName);

    /**
     * 获取所有单例bean名字
     * @return 所有单例bean名字
     */
    String[] getSingletonNames();
}
