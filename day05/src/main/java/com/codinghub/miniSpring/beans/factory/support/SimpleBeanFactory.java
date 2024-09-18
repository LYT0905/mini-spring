package com.codinghub.miniSpring.beans.factory.support;

import com.codinghub.miniSpring.beans.PropertyValue;
import com.codinghub.miniSpring.beans.PropertyValues;
import com.codinghub.miniSpring.beans.factory.BeanFactory;
import com.codinghub.miniSpring.beans.factory.config.BeanDefinition;
import com.codinghub.miniSpring.beans.factory.config.ConstructorArgumentValue;
import com.codinghub.miniSpring.beans.factory.config.ConstructorArgumentValues;
import com.codinghub.miniSpring.common.exception.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 莱特0905
 * @Description: 简单的Bean工厂实现对象
 * @Date: 2024/09/09 16:42:23
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    protected Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    private List<String> beanDefinitionNames = new ArrayList<>();
    private final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>(16);
    public SimpleBeanFactory(){}

    /**
     * 容器的核心方法,获取Bean实例对象
     * @param beanName Bean实例名字
     * @return Bean实例对象
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿Bean实例
        Object singleton = this.singletons.get(beanName);
        // //如果此时还没有这个bean的实例，则获取它的定义来创建实例
        if (singleton == null){
            // 如果没有实例，则尝试从毛胚实例中获取
            singleton = this.earlySingletonObjects.get(beanName);
            if (singleton == null){
                // 预留beanpostprocessor位置
                // step 1: postProcessBeforeInitialization
                // step 2: afterPropertiesSet
                // step 3: init-method
                // step 4: postProcessAfterInitialization
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if (beanDefinition == null){
                    throw new BeansException("get singleton failed,the singleton is not exist");
                }
                try {
                    singleton = Class.forName(beanDefinition.getClassName()).newInstance();
                }catch (Throwable ex){
                    throw new BeansException("create singleton:" + beanName + " failed");
                }
                this.registerSingleton(beanName, singleton);
            }
        }
        return singleton;
    }

    /**
     * 创建Bean实例对象
     * @param beanDefinition Bean实例对象
     * @return Bean实例对象
     */
    private Object createBean(BeanDefinition beanDefinition){
        Class<?> clz = null;
        Object obj = doCreateBean(beanDefinition);
        this.earlySingletonObjects.put(beanDefinition.getId(), obj);
        try {
            clz = Class.forName(beanDefinition.getClassName());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        handleProperties(beanDefinition, clz, obj);
        return obj;
    }

    /**
     * doCreateBean创建毛胚实例，仅仅调用构造方法，没有进行属性处理
     * @param bd 毛胚实例
     * @return 毛胚实例
     */
    private Object doCreateBean(BeanDefinition bd){
        Class<?> clz = null;
        Object obj = null;
        Constructor<?> con = null;
        try {
            clz = Class.forName(bd.getClassName());
            // 处理构造器参数
            ConstructorArgumentValues constructorArgumentValues = bd.getConstructorArgumentsValues();
            // 如果有参数
            if (!constructorArgumentValues.isEmpty()){
                // 参数类型
                Class<?>[] paramTypes = new Class<?>[constructorArgumentValues.getArgumentCount()];
                // 参数值
                Object[] paramValues = new Object[constructorArgumentValues.getArgumentCount()];
                // 对每一个参数，分数据类型分别处理
                for (int i = 0; i < constructorArgumentValues.getArgumentCount(); i++) {
                    ConstructorArgumentValue constructorArgumentValue = constructorArgumentValues.getIndexedArgumentValue(i);
                    if ("String".equals(constructorArgumentValue.getType()) || "java.lang.String".equals(constructorArgumentValue.getType())){
                        paramTypes[i] = String.class;
                        paramValues[i] = constructorArgumentValue.getValue();
                    } else if ("Integer".equals(constructorArgumentValue.getType()) || "java.lang.Integer".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf((String) constructorArgumentValue.getValue());
                    } else if ("int".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf((String) constructorArgumentValue.getValue());
                    }else { // 默认为string
                        paramTypes[i] = String.class;
                        paramValues[i] = constructorArgumentValue.getValue();
                    }
                }
                try {
                    // 按照特定构造器创建实例
                    con = clz.getConstructor(paramTypes);
                    obj = con.newInstance(paramValues);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else { // 如果没有参数，直接创建实例
                obj = clz.newInstance();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(bd.getId() + " bean created. " + bd.getClassName() + " : " + obj.toString());
        return obj;
    }

    /**
     * 处理配置参数
     * @param bd Bean实例
     * @param clz 字节类型
     * @param obj 实体
     */
    private void handleProperties(BeanDefinition bd, Class<?> clz, Object obj) {
        System.out.println("handle properties for bean : " + bd.getId());
        // 处理属性
        PropertyValues propertyValues = bd.getPropertyValues();
        if (!propertyValues.isEmpty()){
            for (int i = 0; i < propertyValues.size(); i++) {
                // 对每一个属性，分数据类型分别处理
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String pType = propertyValue.getType();
                String pName = propertyValue.getName();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.getIsRef();
                Object[] paramValues = new Object[1];
                Class<?>[] paramTypes = new Class<?>[1];
                if (!isRef){ // 如果不是ref，
                    // 对每一个属性，分数据类型分别处理
                    if ("String".equals(pType) || "java.lang.String".equals(pType)){
                        paramTypes[0] = String.class;
                    }else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)){
                        paramTypes[0] = Integer.class;
                    } else if ("int".equals(pType)) {
                        paramTypes[0] = int.class;
                    }else { // 默认为string
                        paramTypes[0] = String.class;
                    }
                    paramValues[0] = pValue;
                }else { // 被依赖，创建依赖的Bean实例
                    try {
                        paramTypes[0] = Class.forName(pType);
                    }catch (ClassNotFoundException e){
                        e.printStackTrace();
                    }
                    try {
                        //再次调用getBean创建ref的bean实例
                        paramValues[0] = getBean((String)pValue);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                // 按照setXxxx规范查找setter方法，调用setter方法设置属性
                String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
                Method method = null;
                try {
                    method = clz.getMethod(methodName, paramTypes);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                try{
                    method.invoke(obj, paramValues);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    public void refresh(){
        for (String beanDefinitionName : beanDefinitionNames) {
            try {
                getBean(beanDefinitionName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }


//    /**
//     * 注册Bean定义对象
//     * @param beanDefinition Bean定义对象
//     */
//    public void registerBeanDefinition(BeanDefinition beanDefinition){
//        this.beanDefinitionMap.put(beanDefinition.getId(), beanDefinition);
//    }

    /**
     * 判断是否包含Bean对象
     * @param beanName  Bean对象名字
     * @return 存在返回true,不存在返回false
     */
    public Boolean containsBean(String beanName){
        return containsSingleton(beanName);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        this.beanDefinitionMap.put(name, bd);
        this.beanDefinitionNames.add(name);
        if (!bd.isLazyInit()){
            try {
                getBean(name);
            }catch (BeansException e){
            }
        }
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }
}
