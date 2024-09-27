package com.codinghub.miniSpring.beans.factory.config;

import com.codinghub.miniSpring.beans.PropertyValues;

/**
 * @author 莱特0905
 * @Description: 定义Bean
 * @Date: 2024/09/09 15:16:17
 */
public class BeanDefinition {
    /**
     * 单例模式
     */
    String SCOPE_SINGLETON = "singleton";

    /**
     * 原型模式
     */
    String SCOPE_PROTOTYPE = "prototype";
    /**
     * Bean 的 ID(也是beanName)
     */
    private String id;
    /**
     * 类名
     */
    private String className;

    /**
     * 是否启用懒加载，默认不启用 (要不要在加载的时候初始化)
     */
    private boolean lazyInit = false;

    /**
     * 依赖的对象集合(记录Bean之间的依赖关系)
     */
    private String[] dependsOn;

    /**
     * 以构造器方式注入的实体对象
     */
    private ConstructorArgumentValues constructorArgumentsValue;

    /**
     * 以setter方式注入的实体对象
     */
    private PropertyValues propertyValues;

    /**
     * 初始化方法的名字
     */
    private String initMethodName;

    /**
     * bean对象class
     */
    private volatile Object beanClass;

    /**
     * 作用范围，默认是单例
     */
    private String scope = SCOPE_SINGLETON;

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public BeanDefinition() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String[] getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String[] dependsOn) {
        this.dependsOn = dependsOn;
    }

    public ConstructorArgumentValues getConstructorArgumentsValues() {
        return constructorArgumentsValue == null ? null : constructorArgumentsValue;
    }

    public void setConstructorArgumentsValue(ConstructorArgumentValues constructorArgumentsValue) {
        this.constructorArgumentsValue = constructorArgumentsValue;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public Object getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Object beanClass) {
        this.beanClass = beanClass;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isSingleton(){
        return this.scope.equals(SCOPE_SINGLETON);
    }

    public boolean isPrototype(){
        return this.scope.equals(SCOPE_PROTOTYPE);
    }
}
