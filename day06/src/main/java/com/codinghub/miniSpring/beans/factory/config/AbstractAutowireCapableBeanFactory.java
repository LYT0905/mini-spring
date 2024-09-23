package com.codinghub.miniSpring.beans.factory.config;

import com.codinghub.miniSpring.beans.BeansException;
import com.codinghub.miniSpring.beans.factory.support.AbstractBeanFactory;


import java.util.ArrayList;
import java.util.List;

/**
 * @author 莱特0905
 * @Description: 自动装配Bean工厂的抽象类
 * @Date: 2024/09/18 20:34:58
 */
public abstract class AbstractAutowireCapableBeanFactory
        extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /**
     * Bean初始化前的处理动作
     * @param existingBean 存在的Bean实例
     * @param beanName Bean名字
     * @return 处理完的Bean实例
     * @throws BeansException 自定义异常
     */
    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            beanProcessor.setBeanFactory(this);
            result = beanProcessor.postProcessorBeforeInitialization(result, beanName);
            if (result == null){
                return result;
            }
        }
        return result;
    }

    /**
     * Bean初始化后的处理动作
     * @param existingBean 存在的Bean实例
     * @param beanName Bean名字
     * @return 处理完的Bean实例
     * @throws BeansException 自定义异常
     */
    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            result = beanProcessor.postProcessorAfterInitialization(result, beanName);
            if (result == null){
                return result;
            }
        }
        return result;
    }

    /**
     * 添加Bean后处理对象处理器
     * @param beanPostProcessor Bean处理对象处理器
     */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * 获取Bean后对象处理器数量
     * @return Bean后对象处理器数量
     */
    @Override
    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    public List<BeanPostProcessor> getBeanPostProcessors(){
        return this.beanPostProcessors;
    }
}
