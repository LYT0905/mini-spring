package com.codinghub.miniSpring.beans.factory;

import com.codinghub.miniSpring.BeanDefinition;
import com.codinghub.miniSpring.common.exception.BeanException;

/**
 * @author 莱特0905
 * @Description: 处理Bean的工厂
 * @Date: 2024/09/09 16:21:01
 */
public interface BeanFactory {
    /**
     * 获取Bean实例
     * @param beanName Bean实例名字
     * @return Bean实例
     */
    Object getBean(String beanName) throws BeanException;

    /**
     * 注册Bean实例
     * @param beanDefinition bean实例
     */
    void registerBeanDefinition(BeanDefinition beanDefinition);

}
