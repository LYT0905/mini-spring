package com.codinghub.miniSpring.beans.factory.config;

import com.codinghub.miniSpring.common.exception.BeansException;

/**
 * @author 莱特0905
 * @Description: Bean初始化前后执行的动作接口
 * @Date: 2024/09/13 19:27:09
 */
public interface BeanPostProcessor {
    /**
     * Bean初始化之前执行的动作
     * @param bean Bean实例
     * @param beanName Bean名字
     * @return 执行完的Bean
     * @throws BeansException 自定义异常
     */
    Object postProcessorBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * Bean初始化之后执行的动作
     * @param bean Bean实例
     * @param beanName Bean名字
     * @return 执行完的Bean
     * @throws BeansException 自定义异常
     */
    Object postProcessorAfterInitialization(Object bean, String beanName) throws BeansException;
}
