package com.codinghub.miniSpring.beans.factory.xml;

import com.codinghub.miniSpring.BeanDefinition;
import com.codinghub.miniSpring.beans.factory.BeanFactory;
import com.codinghub.miniSpring.core.Resources;
import org.dom4j.Element;

/**
 * @author 莱特0905
 * @Description: 读取配置在Xml文件中的Bean
 * @Date: 2024/09/09 16:36:22
 */
public class XmlBeanDefinitionReader {
    BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resources resources){
        while (resources.hasNext()){
            Element element = (Element) resources.next();
            String id = element.attributeValue("id");
            String className = element.attributeValue("className");
            BeanDefinition beanDefinition = new BeanDefinition(id, className);
            beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
