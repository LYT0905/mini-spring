package com.codinghub.miniSpring.beans.factory.support;

import com.codinghub.miniSpring.beans.PropertyValue;
import com.codinghub.miniSpring.beans.PropertyValues;
import com.codinghub.miniSpring.beans.factory.BeanFactory;
import com.codinghub.miniSpring.beans.factory.config.BeanDefinition;
import com.codinghub.miniSpring.beans.factory.config.ConfigurableBeanFactory;
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

public abstract class AbstractBeanFactory
        extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory,BeanDefinitionRegistry {
    protected Map< String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap< >(256);
    protected List< String > beanDefinitionNames = new ArrayList< >();
    private final Map < String, Object > earlySingletonObjects = new HashMap< >(16);
    public AbstractBeanFactory() {}

    /**
     * 将Bean实例放入容器中
     */
    public void refresh() {
        for (String beanName: beanDefinitionNames) {
            try {
                getBean(beanName);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * 获取Bean实例
     * @param beanName Bean实例名字
     * @return Bean实例
     * @throws BeansException 自定义异常
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        //先尝试直接从容器中获取bean实例
        Object singleton = this.getSingleton(beanName);
        if (singleton == null) {
            //如果没有实例，则尝试从毛胚实例中获取
            singleton = this.earlySingletonObjects.get(beanName);
            if (singleton == null) {
                //如果连毛胚都没有，则创建bean实例并注册
                System.out.println("get bean null -------------- " + beanName);
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if (beanDefinition != null) {
                    singleton = createBean(beanDefinition);
                    this.registerBean(beanName, singleton);
                    // 进行beanpostprocessor处理
                    // step 1: postProcessBeforeInitialization Bean初始化前处理
                    applyBeanPostProcessorBeforeInitialization(singleton, beanName);
                    // step 2: init-method 初始化方法
                    if (beanDefinition.getInitMethodName() != null &&
                            !beanDefinition.getInitMethodName().equals("")) {
                        invokeInitMethod(beanDefinition, singleton);
                    }
                    // step 3: postProcessAfterInitialization Bean初始化后处理
                    applyBeanPostProcessorAfterInitialization(singleton, beanName);
                }else {
                    return null;
                }
            }
        }

        return singleton;
    }

    /**
     * 执行初始化方法
     * @param beanDefinition Bean对象
     * @param obj Bean实例后对象
     */
    private void invokeInitMethod(BeanDefinition beanDefinition, Object obj) {
        Class <?> clz = obj.getClass();
        Method method = null;
        try {
            method = clz.getMethod(beanDefinition.getInitMethodName());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        try {
            method.invoke(obj);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 判断容器是否包含Bean实例对象
     * @param name Bean名字
     * @return 包含返回true,不包含返回false
     */
    @Override
    public Boolean containsBean(String name) {
        return containsSingleton(name);
    }

    /**
     * 注册Bean实例
     * @param beanName Bean实例名字
     * @param obj Bean实例化的结果
     */
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    /**
     * 注册Bean初始前的样子
     * @param name bean名字
     * @param beanDefinition bean实例对象
     */
    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
        if (beanDefinition.isLazyInit()) {
            try {
                getBean(name);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * 从容器中移除Bean
     * @param name Bean名字
     */
    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    /**
     * 获取Bean未实例的样子
     * @param name Bean名字
     * @return
     */
    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    /**
     * 是否包含Bean未实例的样子
     * @param name Bean名字
     * @return
     */
    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    /**
     * Bean是否是单例
     * @param name Bean名字
     * @return 是单例返回true,否则返回false
     */
    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    /**
     * Bean是否是原型
     * @param name Bean名字
     * @return 是原型返回true,否则返回false
     */
    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    /**
     * 获取Bean类型
     * @param name Bean名字
     * @return Bean类型
     */
    @Override
    public Class < ? > getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }

    /**
     * 创建Bean实例
     * @param beanDefinition Bean未实例的样子
     * @return Bean实例
     */
    private Object createBean(BeanDefinition beanDefinition) {
        Class < ? > clz = null;
        //创建毛胚bean实例
        Object obj = doCreateBean(beanDefinition);
        //存放到毛胚实例缓存中
        this.earlySingletonObjects.put(beanDefinition.getId(), obj);
        try {
            clz = Class.forName(beanDefinition.getClassName());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        //完善bean，主要是处理属性
        populateBean(beanDefinition, clz, obj);
        return obj;
    }

    /**
     * doCreateBean创建毛胚实例，仅仅调用构造方法，没有进行属性处理
     * @param beanDefinition Bean未实例的样子
     * @return 毛胚实例
     */
    private Object doCreateBean(BeanDefinition beanDefinition) {
        Class < ? > clz = null;
        Object obj = null;
        Constructor< ? > con = null;
        try {
            clz = Class.forName(beanDefinition.getClassName());
            // handle constructor
            ConstructorArgumentValues constructorArgumentValues =
                    beanDefinition.getConstructorArgumentsValues();
            if (constructorArgumentValues != null) {
                if (!constructorArgumentValues.isEmpty()) {
                    Class<?>[] paramTypes = new Class<?>[constructorArgumentValues.getArgumentCount()];
                    Object[] paramValues = new
                            Object[constructorArgumentValues.getArgumentCount()];
                    for (int i = 0; i <
                            constructorArgumentValues.getArgumentCount(); i++) {
                        ConstructorArgumentValue constructorArgumentValue =
                                constructorArgumentValues.getIndexedArgumentValue(i);
                        if ("String".equals(constructorArgumentValue.getType()) ||
                                "java.lang.String".equals(constructorArgumentValue.getType())) {
                            paramTypes[i] = String.class;
                            paramValues[i] = constructorArgumentValue.getValue();
                        } else if ("Integer".equals(constructorArgumentValue.getType()) ||
                                "java.lang.Integer".equals(constructorArgumentValue.getType())) {
                            paramTypes[i] = Integer.class;
                            paramValues[i] = Integer.valueOf((String) constructorArgumentValue.getValue());
                        } else if ("int".equals(constructorArgumentValue.getType())) {
                            paramTypes[i] = int.class;
                            paramValues[i] = Integer.valueOf((String) constructorArgumentValue.getValue());
                        } else {
                            paramTypes[i] = String.class;
                            paramValues[i] = constructorArgumentValue.getValue();
                        }
                    }
                    try {
                        con = clz.getConstructor(paramTypes);
                        obj = con.newInstance(paramValues);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }else {
                    obj = clz.newInstance();
                }
            }else {
                obj = clz.newInstance();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        System.out.println(beanDefinition.getId() + " bean created. " +
                beanDefinition.getClassName() + " : " + obj.toString());
        return obj;
    }

    /**
     * 对属性参数进行处理，handleProperties()方法的封装
     * @param beanDefinition Bean未实例的样子
     * @param clz class类型
     * @param obj 毛胚实例
     */
    private void populateBean(BeanDefinition beanDefinition, Class < ? > clz,
                              Object obj) {
        handleProperties(beanDefinition, clz, obj);
    }

    /**
     * 对属性参数进行处理
     * @param beanDefinition Bean未实例的样子
     * @param clz class类型
     * @param obj 毛胚实例
     */
    private void handleProperties(BeanDefinition beanDefinition, Class < ? > clz,
                                  Object obj) {
        // handle properties
        System.out.println("handle properties for bean : " +
                beanDefinition.getId());
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        //如果有属性
        if (!propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.size(); i++) {
                PropertyValue propertyValue =
                        propertyValues.getPropertyValueList().get(i);
                String pType = propertyValue.getType();
                String pName = propertyValue.getName();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.getIsRef();
                Class < ? > [] paramTypes = new Class < ? > [1];
                Object[] paramValues = new Object[1];
                if (!isRef) { //如果不是ref，只是普通属性
                    //对每一个属性，分数据类型分别处理
                    if ("String".equals(pType) ||
                            "java.lang.String".equals(pType)) {
                        paramTypes[0] = String.class;
                        paramValues[0] = pValue;
                    } else if ("Integer".equals(pType) ||
                            "java.lang.Integer".equals(pType)) {
                        paramTypes[i] = Integer.class;
                        paramValues[0] = Integer.valueOf((String) pValue);
                    } else if ("int".equals(pType)) {
                        paramTypes[i] = int.class;
                        paramValues[0] = Integer.valueOf((String) pValue).intValue();
                    } else {
                        paramTypes[i] = String.class;
                        paramValues[0] = pValue;
                    }
                } else { //is ref, create the dependent beans
                    try {
                        paramTypes[0] = Class.forName(pType);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    try { //再次调用getBean创建ref的bean实例
                        paramValues[0] = getBean((String) pValue);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                //按照setXxxx规范查找setter方法，调用setter方法设置属性
                String methodName = "set" + pName.substring(0, 1).toUpperCase() +
                        pName.substring(1);
                Method method = null;
                try {
                    method = clz.getMethod(methodName, paramTypes);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                try {
                    method.invoke(obj, paramValues);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 抽象方法，Bean初始化前的处理动作
     * @param existingBean 存在的Bean实例
     * @param beanName Bean名字
     * @return 处理完的Bean实例
     * @throws BeansException 自定义异常
     */
    abstract public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 抽象方法，Bean初始化后的处理动作
     * @param existingBean 存在的Bean实例
     * @param beanName Bean名字
     * @return 处理完的Bean实例
     * @throws BeansException 自定义异常
     */
    abstract public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException;
}