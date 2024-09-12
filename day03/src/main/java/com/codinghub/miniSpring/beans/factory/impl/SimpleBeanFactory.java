package com.codinghub.miniSpring.beans.factory.impl;

import com.codinghub.miniSpring.beans.factory.BeanFactory;
import com.codinghub.miniSpring.beans.factory.config.BeanDefinition;
import com.codinghub.miniSpring.beans.factory.support.BeanDefinitionRegistry;
import com.codinghub.miniSpring.beans.factory.support.DefaultSingletonBeanRegistry;
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
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    protected Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    private List<String> beanDefinitionNames = new ArrayList<>();
    public SimpleBeanFactory(){}

    /**
     * 容器的核心方法,获取Bean实例对象
     * @param beanName Bean实例名字
     * @return Bean实例对象
     */
    @Override
    public Object getBean(String beanName) throws BeanException {
        // 先尝试直接拿Bean实例
        Object singleton = this.singletons.get(beanName);
        // //如果此时还没有这个bean的实例，则获取它的定义来创建实例
        if (singleton == null){
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition == null){
                throw new BeanException("get singleton failed,the singleton is not exist");
            }
            try {
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            }catch (Throwable ex){
                throw new BeanException("create singleton:" + beanName + " failed");
            }
            this.registrySingleton(beanName, beanDefinition);
        }
        return singleton;
    }


    /**
     * 注册Bean定义对象
     * @param beanDefinition Bean定义对象
     */
    public void registerBeanDefinition(BeanDefinition beanDefinition){
        this.beanDefinitionMap.put(beanDefinition.getId(), beanDefinition);
    }

    /**
     * 判断是否包含Bean对象
     * @param beanName  Bean对象名字
     * @return 存在返回true,不存在返回false
     */
    public Boolean containsBean(String beanName){
        return containsSingleton(beanName);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        this.beanDefinitionMap.put(name, bd);
        this.beanDefinitionNames.add(name);
        if (!bd.isLazyInit()){
            try {
                getBean(name);
            }catch (BeanException e){
            }
        }
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }
}
