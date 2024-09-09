package com.codinghub.miniSpring.beans.factory.impl;

import com.codinghub.miniSpring.BeanDefinition;
import com.codinghub.miniSpring.beans.factory.BeanFactory;
import com.codinghub.miniSpring.common.exception.BeanException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 莱特0905
 * @Description: 简单的Bean工厂实现对象
 * @Date: 2024/09/09 16:42:23
 */
public class SimpleBeanFactory implements BeanFactory {
    List<BeanDefinition> beanDefinitions = new ArrayList<>();
    List<String> beanNames = new ArrayList<>();
    Map<String, Object> singletons = new ConcurrentHashMap<>();

    public SimpleBeanFactory(){}

    /**
     * 容器的核心方法,获取Bean实例对象
     * @param beanName Bean实例名字
     * @return
     */
    @Override
    public Object getBean(String beanName) throws BeanException {
        // 先尝试直接拿Bean实例
        Object bean = singletons.get(beanName);
        if (bean == null){
            // 根据bean实例名字获取索引
            int i = beanNames.indexOf(beanName);
            if (i == -1){
                throw new BeanException("get bean failed,the bean is not exist");
            }
            // 根据索引获取Bean实例
            BeanDefinition beanDefinition = beanDefinitions.get(i);
            try {
                bean = Class.forName(beanDefinition.getClassName()).newInstance();
                singletons.put(beanDefinition.getId(), bean);
            }catch (Throwable ex){
                throw new BeanException("create bean:" + beanName + " failed");
            }
        }
        return bean;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.add(beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }
}
