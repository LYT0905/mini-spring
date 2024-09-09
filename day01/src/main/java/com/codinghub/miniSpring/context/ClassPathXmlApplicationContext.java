package com.codinghub.miniSpring.context;

import com.codinghub.miniSpring.BeanDefinition;
import com.codinghub.miniSpring.beans.factory.BeanFactory;
import com.codinghub.miniSpring.beans.factory.impl.SimpleBeanFactory;
import com.codinghub.miniSpring.beans.factory.xml.XmlBeanDefinitionReader;
import com.codinghub.miniSpring.common.exception.BeanException;
import com.codinghub.miniSpring.core.ClassPathXmlResource;
import com.codinghub.miniSpring.core.Resources;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 莱特0905
 * @Description: 路径下的Xml文件解析上下文
 * @Date: 2024/09/09 15:32:01
 */
public class ClassPathXmlApplicationContext implements BeanFactory {
    BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName){
        Resources resources = new ClassPathXmlResource(fileName);
        BeanFactory simpleBeanFactory = new SimpleBeanFactory();
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

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }

}
