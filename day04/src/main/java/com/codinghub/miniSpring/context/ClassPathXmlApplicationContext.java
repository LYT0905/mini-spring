package com.codinghub.miniSpring.context;

import com.codinghub.miniSpring.beans.factory.BeanFactory;
import com.codinghub.miniSpring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.codinghub.miniSpring.beans.factory.config.AutowireCapableBeanFactory;
import com.codinghub.miniSpring.beans.factory.config.BeanFactoryPostProcessor;
import com.codinghub.miniSpring.beans.factory.config.BeanPostProcessor;
import com.codinghub.miniSpring.beans.factory.support.SimpleBeanFactory;
import com.codinghub.miniSpring.beans.factory.xml.XmlBeanDefinitionReader;
import com.codinghub.miniSpring.common.exception.BeansException;
import com.codinghub.miniSpring.core.ClassPathXmlResource;
import com.codinghub.miniSpring.core.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莱特0905
 * @Description: 路径下的Xml文件解析上下文
 * @Date: 2024/09/09 15:32:01
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {
    AutowireCapableBeanFactory beanFactory;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors =
            new ArrayList<BeanFactoryPostProcessor>();

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh){
        Resources resources = new ClassPathXmlResource(fileName);
        //SimpleBeanFactory simpleBeanFactory = new SimpleBeanFactory();
        AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(resources);
        this.beanFactory = beanFactory;
        if (isRefresh){
            try {
                refresh();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors(){
        return this.beanFactoryPostProcessors;
    }

    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }
    public void refresh() throws BeansException, IllegalStateException {
        // Register bean processors that intercept bean creation.
        registerBeanPostProcessors(this.beanFactory);
        // Initialize other special beans in specific context subclasses.
        onRefresh();
    }
    private void registerBeanPostProcessors(AutowireCapableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }
    private void onRefresh() {
        this.beanFactory.refresh();
    }

    /**
     *  这是对外的一个方法，让外部程序从容器中获取Bean实例，会逐步演化成核心方法
     * @return Bean实例
     */
    public Object getBean(String beanName) throws BeansException {
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

    @Override
    public void publishEvent(ApplicationEvent event) {

    }
}
