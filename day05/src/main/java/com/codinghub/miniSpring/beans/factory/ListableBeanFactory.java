package com.codinghub.miniSpring.beans.factory;

import com.codinghub.miniSpring.common.exception.BeansException;

import java.util.Map;

/**
 * @author 莱特0905
 * @Description: 增强BeanFactory扩展性
 * @Date: 2024/09/18 19:54:40
 */
public interface ListableBeanFactory extends BeanFactory{
    /**
     * 判断是否包含Bean
     * @param beanName Bean名字
     * @return 包含返回true，否则返回false
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 获取Bean的数量
     * @return Bean的数量
     */
    int getBeanDefinitionCount();

    /**
     * 获取Bean的名字集合
     * @return Bean的名字集合
     */
    String[] getBeanDefinitionNames();

    /**
     * 通过Bean的类型获取Bean的名字
     * @param type Bean的类型
     * @return Bean的名字
     */
    String[] getBeanNamesForType(Class<?> type);

    /**
     * 根据Bean类型获取Bean
     * @param type Bean类型
     * @return Bean
     * @param <T> 泛型
     * @throws BeansException 自定义异常
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}
