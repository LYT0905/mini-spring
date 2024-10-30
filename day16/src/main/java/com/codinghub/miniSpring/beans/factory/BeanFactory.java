package com.codinghub.miniSpring.beans.factory;


import com.codinghub.miniSpring.beans.BeansException;

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
    Object getBean(String beanName) throws BeansException;

    /**
     * 根据Bean名字判断是否存在Bean实例
     * @param beanName Bean名字
     * @return 存在返回true,不存在返回false
     */
    Boolean containsBean(String beanName);

    /**
     * 根据Bean名字判断Bean对象是否是单例模式
     * @param name Bean名字
     * @return 是则返回true,否则返回false
     */
    boolean isSingleton(String name);

    /**
     * 根据Bean名字判断Bean对象是否是原型模式
     * @param name Bean名字
     * @return 是则返回true,否则返回false
     */
    boolean isPrototype(String name);

    /**
     * 根据Bean名字获取Bean的类型
     * @param name Bean名字
     * @return Bean的类型
     */
    Class<?> getType(String name);

}
