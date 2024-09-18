package com.codinghub.miniSpring.beans.factory.support;

import com.codinghub.miniSpring.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 莱特0905
 * @Description: 默认单例Bean注册实现类
 * @Date: 2024/09/12 11:02:00
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    // 存放所有Bean的名字
    protected List<String> beanNames = new ArrayList<>();
    //容器中存放所有bean实例的map
    protected  Map<String, Object> singletons = new ConcurrentHashMap<>(256);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletons){
            Object oldObject = this.singletons.get(beanName);
            if (oldObject != null) {
                throw new IllegalStateException("Could not register object [" + singletonObject +
                        "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
            }
            this.beanNames.add(beanName);
            this.singletons.put(beanName, singletonObject);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletons.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return (String[]) this.beanNames.toArray();
    }

    /**
     * 移除单例Bean
     * @param beanName 单例Bean名字
     */
    protected void removeSingleton(String beanName){
        synchronized (this.singletons){
            this.singletons.remove(beanName);
            this.beanNames.remove(beanName);
        }
    }
}
