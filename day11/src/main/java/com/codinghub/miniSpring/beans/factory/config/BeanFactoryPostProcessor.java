package com.codinghub.miniSpring.beans.factory.config;

import com.codinghub.miniSpring.beans.BeansException;
import com.codinghub.miniSpring.beans.factory.BeanFactory;


/**
 * @author 莱特0905
 * @Description:
 * @Date: 2024/09/13 20:57:31
 */
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;
}
