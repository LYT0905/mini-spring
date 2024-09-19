package com.codinghub.miniSpring.beans.factory.support;

import com.codinghub.miniSpring.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.codinghub.miniSpring.beans.factory.config.BeanDefinition;
import com.codinghub.miniSpring.beans.factory.config.ConfigurableListableBeanFactory;
import com.codinghub.miniSpring.common.exception.BeansException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 莱特0905
 * @Description: 默认的可列表Bean工厂
 * @Date: 2024/09/18 21:04:13
 */
public class DefaultListableBeanFactory
        extends AbstractAutowireCapableBeanFactory
        implements ConfigurableListableBeanFactory {
    /**
     * 获取Bean的数量
     * @return Bean的数量
     */
    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    /**
     * 获取Bean的名字集合
     * @return Bean的名字集合
     */
    @Override
    public String[] getBeanDefinitionNames() {
        return (String[]) this.beanDefinitionNames.toArray();
    }

    /**
     * 通过Bean的类型获取Bean的名字
     * @param type Bean的类型
     * @return Bean的名字
     */
    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> result = new ArrayList<>();
        for (String beanName : this.beanDefinitionNames) {
            boolean matchFound = false;
            BeanDefinition mbd = this.getBeanDefinition(beanName);
            Class<?> classToMatch = mbd.getClass();
            // 判断参数的type是否可以从此时的bean中分配过来，自认为可以理解为是否是子类或者它自己本身
            if (type.isAssignableFrom(classToMatch)){
                matchFound = true;
            }else {
                matchFound = false;
            }
            if (matchFound){
                result.add(beanName);
            }
        }
        return (String[]) result.toArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        String[] beanNames = getBeanNamesForType(type);
        Map<String, T> result = new LinkedHashMap<>(beanNames.length);
        for (String beanName : beanNames) {
            Object beanInstance = getBean(beanName);
            result.put(beanName, (T) beanInstance);
        }
        return result;
    }
}
