package com.codinghub.miniSpring.beans.factory.annotation;

import com.codinghub.miniSpring.beans.BeansException;
import com.codinghub.miniSpring.beans.factory.BeanFactory;
import com.codinghub.miniSpring.beans.factory.config.BeanPostProcessor;


import java.lang.reflect.Field;

/**
 * @author 莱特0905
 * @Description: 自动注入Bean的处理(Autowired的处理类)
 * @Date: 2024/09/13 20:31:56
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {
    private BeanFactory beanFactory;

    /**
     * Bean初始化之前执行的动作
     * @param bean Bean实例
     * @param beanName Bean名字
     * @return 执行完的Bean
     * @throws BeansException 自定义异常
     */
    @Override
    public Object postProcessorBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null){
            // 对每一个属性进行判断，如果带有@Autowired注解则进行处理
            for (Field field : fields) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if (isAutowired){
                    // 根据属性名查找同名的bean
                    String fieldName = field.getName();
                    Object autowiredObj = this.getBeanFactory().getBean(fieldName);
                    // 设置属性值，完成注入
                    try {
                        field.setAccessible(true);
                        field.set(bean, autowiredObj);
                        System.out.println("autowire " + fieldName + " for bean " + beanName);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    /**
     * Bean初始化之后执行的动作
     * @param bean Bean实例
     * @param beanName Bean名字
     * @return 执行完的Bean
     * @throws BeansException 自定义异常
     */
    @Override
    public Object postProcessorAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory(){
        return beanFactory;
    }
}
