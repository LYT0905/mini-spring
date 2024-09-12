package com.codinghub.miniSpring.context;

import com.codinghub.miniSpring.beans.factory.BeanFactory;
import com.codinghub.miniSpring.beans.factory.impl.SimpleBeanFactory;
import com.codinghub.miniSpring.beans.factory.xml.XmlBeanDefinitionReader;
import com.codinghub.miniSpring.common.exception.BeanException;
import com.codinghub.miniSpring.core.ClassPathXmlResource;
import com.codinghub.miniSpring.core.Resources;

/**
 * @author 莱特0905
 * @Description: 路径下的Xml文件解析上下文
 * @Date: 2024/09/09 15:32:01
 */
public class ClassPathXmlApplicationContext implements BeanFactory {
    SimpleBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName){
        Resources resources = new ClassPathXmlResource(fileName);
        SimpleBeanFactory simpleBeanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(simpleBeanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(resources);
        this.beanFactory = simpleBeanFactory;
    }

    /**
     *  这是对外的一个方法，让外部程序从容器中获取Bean实例，会逐步演化成核心方法
     * @return Bean实例
     */
    public Object getBean(String beanName) throws BeanException {
        return this.beanFactory.getBean(beanName);
    }

    public Boolean containsBean(String beanName){
        return this.beanFactory.containsBean(beanName);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanFactory.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanFactory.isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanFactory.getType(name);
    }

}
