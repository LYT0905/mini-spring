package com.codinghub.miniSpring.beans.factory.support;

import com.codinghub.miniSpring.beans.factory.config.SingletonBeanRegistry;

import java.util.*;
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
    protected  Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
    // 存放依赖Bean的Map
    protected Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);
    // 存放Bean依赖性的Map
    protected Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects){
            Object oldObject = this.singletonObjects.get(beanName);
            if (oldObject != null) {
                throw new IllegalStateException("Could not register object [" + singletonObject +
                        "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
            }
            this.beanNames.add(beanName);
            this.singletonObjects.put(beanName, singletonObject);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletonObjects.containsKey(beanName);
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
        synchronized (this.singletonObjects){
            this.singletonObjects.remove(beanName);
            this.beanNames.remove(beanName);
        }
    }

    /**
     * 注册依赖的Bean对象
     * @param beanName Bean名字
     * @param dependentBeanName 依赖的Bean名字
     */
    public void registerDependentBean(String beanName, String dependentBeanName){
        Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
        if (dependentBeans != null && dependentBeans.contains(dependentBeanName)){
            return;
        }

        // 还没有条目-> 依赖Beans集的完全同步操作
        synchronized (this.dependentBeanMap){
            dependentBeans = this.dependentBeanMap.get(beanName);
            if (dependentBeans == null){
                dependentBeans = new LinkedHashSet<>(8);
                this.dependentBeanMap.put(beanName, dependentBeans);
            }
            dependentBeans.add(dependentBeanName);
        }
        synchronized (this.dependenciesForBeanMap){
            Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(dependentBeanName);
            if (dependenciesForBean == null){
                dependenciesForBean = new LinkedHashSet<>(8);
                this.dependenciesForBeanMap.put(dependentBeanName, dependenciesForBean);
            }
            dependenciesForBean.add(beanName);
        }
    }

    /**
     * 判断该Bean是否有依赖的Bean
     * @param beanName Bean名字
     * @return 有依赖返回true,否则返回false
     */
    public boolean hasDependentBean(String beanName){
        return this.dependentBeanMap.containsKey(beanName);
    }

    /**
     * 获取依赖的Bean
     * @param beanName Bean名字
     * @return 依赖的Bean
     */
    public String[] getDependentBeans(String beanName){
        Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
        if (dependentBeans == null){
            return new String[0];
        }
        return (String[]) dependentBeans.toArray();
    }

    /**
     * 获取Bean的依赖
     * @param beanName Bean名字
     * @return Bean的依赖
     */
    public String[] getDependenciesForBean(String beanName){
        Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(beanName);
        if (dependenciesForBean == null){
            return new String[0];
        }
        return (String[]) dependenciesForBean.toArray();
    }
}
