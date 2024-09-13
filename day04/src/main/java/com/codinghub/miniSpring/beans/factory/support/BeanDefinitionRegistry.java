package com.codinghub.miniSpring.beans.factory.support;

import com.codinghub.miniSpring.beans.factory.config.BeanDefinition;

/**
 * @author 莱特0905
 * @Description: bean定义注册接口(类似于一个存放BeanDefinition的仓库)
 * @Date: 2024/09/12 14:59:56
 */
public interface BeanDefinitionRegistry {
    /**
     * 注册bean实例对象
     * @param name bean名字
     * @param bd bean实例对象
     */
    void registerBeanDefinition(String name, BeanDefinition bd);

    /**
     * 根据Bean名字移除Bean实例对象
     * @param name Bean名字
     */
    void removeBeanDefinition(String name);

    /**
     * 根据Bean名字获取Bean实例对象
     * @param name Bean名字
     * @return Bean实例对象
     */
    BeanDefinition getBeanDefinition(String name);

    /**
     * 根据Bean名字判断是否包含Bean实例对象
     * @param name Bean名字
     * @return 存在返回true,不存在返回false
     */
    boolean containsBeanDefinition(String name);
}
