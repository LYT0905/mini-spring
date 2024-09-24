package com.codinghub.miniSpring.context;

import com.codinghub.miniSpring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.codinghub.miniSpring.beans.factory.config.ConfigurableListableBeanFactory;
import com.codinghub.miniSpring.beans.factory.support.DefaultListableBeanFactory;
import com.codinghub.miniSpring.beans.factory.xml.XmlBeanDefinitionReader;
import com.codinghub.miniSpring.core.ClassPathXmlResource;
import com.codinghub.miniSpring.core.Resources;

/**
 * @author 莱特0905
 * @Description: 路径下的Xml文件解析上下文
 * @Date: 2024/09/09 15:32:01
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    DefaultListableBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh){
        Resources resources = new ClassPathXmlResource(fileName);
        //SimpleBeanFactory simpleBeanFactory = new SimpleBeanFactory();
        //AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();
        DefaultListableBeanFactory bf = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(bf);
        xmlBeanDefinitionReader.loadBeanDefinitions(resources);
        this.beanFactory = bf;
        if (isRefresh){
            try {
                refresh();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);

    }

    @Override
    void initApplicationEventPublisher() {
        ApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(aep);
    }

    @Override
    void postProcessBeanFactory(ConfigurableListableBeanFactory bf) {
    }

    @Override
    void registerBeanPostProcessors(ConfigurableListableBeanFactory bf) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override
    void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);

    }

    @Override
    void finishRefresh() {
        publishEvent(new ContextRefreshEvent("Context Refreshed..."));

    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);

    }
}
