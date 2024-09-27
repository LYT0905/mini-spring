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

    /**
     * 注册监听者
     */
    @Override
    public void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);

    }

    /**
     * 初始化应用事件推送者
     */
    @Override
    public void initApplicationEventPublisher() {
        ApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(aep);
    }

    /**
     * Bean工厂后置处理器
     * @param bf 可配置的可列表Bean工厂
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory bf) {
    }

    /**
     * 注册Bean工厂后置处理器
     * @param bf 可配置的可列表Bean工厂
     */
    @Override
    public void registerBeanPostProcessors(ConfigurableListableBeanFactory bf) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    /**
     * 正在刷新
     */
    @Override
    public void onRefresh() {
        this.beanFactory.refresh();
    }

    /**
     * 获取Bean工厂
     * @return Bean工厂
     * @throws IllegalStateException 非法状态错误
     */
    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    /**
     * 添加应用监听
     * @param listener 监听
     */
    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);

    }

    /**
     * 完成刷新
     */
    @Override
    public void finishRefresh() {
        publishEvent(new ContextRefreshEvent("Context Refreshed..."));

    }

    /**
     * 发布事件
     * @param event 事件
     */
    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);

    }
}
