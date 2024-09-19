package com.codinghub.miniSpring.beans.factory.config;

import com.codinghub.miniSpring.beans.factory.BeanFactory;
import com.codinghub.miniSpring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.codinghub.miniSpring.beans.factory.support.AbstractBeanFactory;
import com.codinghub.miniSpring.common.exception.BeansException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莱特0905
 * @Description: 自动注入处理Bean工厂
 * @Date: 2024/09/13 20:33:30
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    int AUTOWIRED_NO = 0;
    int AUTOWIRED_BY_NAME = 1;
    int AUTOWIRED_BY_TYPE = 2;

    /**
     * Bean初始化前的处理动作
     * @param existingBean 存在的Bean实例
     * @param beanName Bean名字
     * @return 处理完的Bean实例
     * @throws BeansException 自定义异常
     */
    Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * Bean初始化后的处理动作
     * @param existingBean 存在的Bean实例
     * @param beanName Bean名字
     * @return 处理完的Bean实例
     * @throws BeansException 自定义异常
     */
    Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
