package com.codinghub.miniSpring.beans.factory.xml;

import com.codinghub.miniSpring.beans.PropertyValue;
import com.codinghub.miniSpring.beans.PropertyValues;
import com.codinghub.miniSpring.beans.factory.config.ArgumentValue;
import com.codinghub.miniSpring.beans.factory.config.ArgumentValues;
import com.codinghub.miniSpring.beans.factory.config.BeanDefinition;
import com.codinghub.miniSpring.beans.factory.impl.SimpleBeanFactory;
import com.codinghub.miniSpring.core.Resources;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莱特0905
 * @Description: 读取配置在Xml文件中的Bean
 * @Date: 2024/09/09 16:36:22
 */
public class XmlBeanDefinitionReader {
    SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory){
        this.simpleBeanFactory = simpleBeanFactory;
    }

    public void loadBeanDefinitions(Resources resources){
        while (resources.hasNext()){
            Element element = (Element) resources.next();
            String id = element.attributeValue("id");
            String className = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(id, className);

            // 处理属性
            List<Element> propertyElements = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element e : propertyElements) {
                String type = e.attributeValue("type");
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");
                String pRef = e.attributeValue("ref");
                String pV = "";
                boolean isRef = false;
                if (value != null && !value.equals("")){
                    isRef = false;
                    pV = value;
                }else if (pRef != null && !pRef.equals("")){
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                PVS.addPropertyValue(new PropertyValue(type, name, pV, isRef));
            }
            beanDefinition.setPropertyValues(PVS);

            // 处理构造器参数
            List<Element> constructorElements = element.elements("constructor-arg");
            ArgumentValues AVS = new ArgumentValues();
            for (Element e : constructorElements) {
                String type = e.attributeValue("type");
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");
                AVS.addArgumentValue(new ArgumentValue(type, name, value));
            }

            beanDefinition.setConstructorArgumentsValue(AVS);
            // 设置依赖情况
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);

            this.simpleBeanFactory.registerBeanDefinition(beanDefinition.getId(), beanDefinition);
        }
    }
}
