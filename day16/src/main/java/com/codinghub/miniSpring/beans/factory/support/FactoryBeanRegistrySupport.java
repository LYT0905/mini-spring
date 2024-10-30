package com.codinghub.miniSpring.beans.factory.support;

import com.codinghub.miniSpring.beans.BeansException;
import com.codinghub.miniSpring.beans.factory.FactoryBean;

/**
 * @author 莱特0905
 * @Description: 工厂Bean注册支持
 * @Date: 2024/10/25 17:05:28
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry{

    /**
     * 获取工厂Bean的类型
     * @param factoryBean 工厂Bean
     * @return 工厂Bean的类型
     */
    protected Class<?> getTypeForFactoryBean(final FactoryBean<?> factoryBean){
        return factoryBean.getObjectType();
    }

    /**
     * 从工厂Bean获取对象
     * @param factory 工厂
     * @param beanName Bean名字
     * @return 对象
     */
    protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName){
        Object object = doGetObjectFromFactoryBean(factory, beanName);
        try {
            object = postProcessObjectFromFactoryBean(object, beanName);
        }catch (BeansException ex){
            ex.printStackTrace();
        }
        return object;
    }

    /**
     * 执行从工厂Bean获取对象
     * @param factory 工厂
     * @param beanName Bean名字
     * @return 对象
     */
    private Object doGetObjectFromFactoryBean(final FactoryBean<?> factory, final String beanName){
        Object object = null;
        try {
            object = factory.getObject();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return object;
    }

    /**
     * 后处理对象从工厂Bean
     * @param object 实例
     * @param beanName Bean名字
     * @return 实例
     * @throws BeansException 自定义异常
     */
    protected Object postProcessObjectFromFactoryBean(Object object, String beanName) throws BeansException{
        return object;
    }

    /**
     * 获取工厂Bean
     * @param beanName Bean名字
     * @param beanInstance Bean实例
     * @return 工厂Bean
     * @throws BeansException 自定义异常
     */
    protected FactoryBean<?> getFactoryBean(String beanName, Object beanInstance) throws BeansException{
        if (!(beanInstance instanceof FactoryBean)){
            throw new BeansException(
                    "Bean instance of type [" + beanInstance.getClass() + "] is not a FactoryBean");
        }
        return (FactoryBean<?>) beanInstance;
    }
}
