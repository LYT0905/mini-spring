package com.codinghub.miniSpring.web.context.support;

import com.codinghub.miniSpring.beans.BeansException;
import com.codinghub.miniSpring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.codinghub.miniSpring.beans.factory.config.BeanDefinition;
import com.codinghub.miniSpring.beans.factory.config.BeanFactoryPostProcessor;
import com.codinghub.miniSpring.beans.factory.config.BeanPostProcessor;
import com.codinghub.miniSpring.beans.factory.config.ConfigurableListableBeanFactory;
import com.codinghub.miniSpring.beans.factory.support.DefaultListableBeanFactory;
import com.codinghub.miniSpring.context.*;
import com.codinghub.miniSpring.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 莱特0905
 * @Description: 注解配置的应用程序
 * @Date: 2024/09/25 19:16:43
 */
public class AnnotationConfigWebApplicationContext extends AbstractApplicationContext implements WebApplicationContext {

    private WebApplicationContext parentWebApplicationContext;
    private ServletContext servletContext;
    DefaultListableBeanFactory beanFactory;
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public AnnotationConfigWebApplicationContext(String fileName) {
        this(fileName, null);
    }

    public AnnotationConfigWebApplicationContext(String fileName, WebApplicationContext parentWebApplicationContext) {
        this.parentWebApplicationContext = parentWebApplicationContext;
        this.servletContext = this.parentWebApplicationContext.getServletContext();
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(fileName);
        }catch (MalformedURLException ex){
            ex.printStackTrace();
        }
        List<String> packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        List<String> controllerNames = scanPackages(packageNames);
        DefaultListableBeanFactory bf = new DefaultListableBeanFactory();
        this.beanFactory = bf;
        this.beanFactory.setParent(this.parentWebApplicationContext.getBeanFactory());
        loadBeanDefinitions(controllerNames);

        if (true){
            try {
                refresh();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加载Bean
     * @param controllerNames 控制层名字
     */
    public void loadBeanDefinitions(List<String> controllerNames){
        for (String controllerName : controllerNames) {
            String beanID = controllerName;
            String beanClassName = controllerName;
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            this.beanFactory.registerBeanDefinition(beanID, beanDefinition);
        }
    }


    /**
     * 扫描定义的包
     * @param packages 定义的包
     * @return 包下面的所有文件
     */
    private List<String> scanPackages(List<String> packages){
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packages) {
            tempControllerNames.addAll(scanPackage(packageName));
        }
        return tempControllerNames;
    }

    /**
     * 扫描单个包
     * @param packageName 包名
     * @return 包下面的所有文件
     */
    private List<String> scanPackage(String packageName){
        List<String> tempControllerNames = new ArrayList<>();
        URL url = this.getClass().getClassLoader().getResource( packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()){
                scanPackage(packageName +"." + file.getName());
            }else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        return tempControllerNames;
    }

    /**
     * 设置父级容器上下文
     * @param parentWebApplicationContext 父级容器上下文
     */
    public void setParent(WebApplicationContext parentWebApplicationContext){
        this.parentWebApplicationContext = parentWebApplicationContext;
        this.beanFactory.setParent(this.parentWebApplicationContext.getBeanFactory());
    }

    /**
     * 获取程序上下文事件
     * @return 程序上下文事件
     */
    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    /**
     * 设置程序上下文事件
     * @param servletContext 程序上下文事件
     */
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
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
     * 发布事件
     * @param event 事件
     */
    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
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
     * 注册监听者对象
     */
    @Override
    public void registerListeners(){
        String[] bdNames = this.beanFactory.getBeanDefinitionNames();
        for (String bdName : bdNames) {
            Object bean = null;
            try {
                bean = getBean(bdName);
            } catch (BeansException e1) {
                e1.printStackTrace();
            }

            if (bean instanceof ApplicationListener) {
                this.getApplicationEventPublisher().addApplicationListener((ApplicationListener<?>) bean);
            }
        }
    }

    @Override
    public void initApplicationEventPublisher() {
        ApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(aep);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory bf) {
    }

    @Override
    public void registerBeanPostProcessors(ConfigurableListableBeanFactory bf) {
        try {
            this.beanFactory.addBeanPostProcessor((BeanPostProcessor) (this.beanFactory.getBean("autowiredAnnotationBeanPostProcessor")));
        }catch (BeansException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    public void finishRefresh() {
        // TODO Auto-generated method stub

    }

}
