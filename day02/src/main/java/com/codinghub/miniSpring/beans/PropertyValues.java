package com.codinghub.miniSpring.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莱特0905
 * @Description: 依赖注入实体对象集合
 * @Date: 2024/09/12 14:35:30
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList;

    public PropertyValues(){
        this.propertyValueList = new ArrayList<>(0);
    }

    /**
     * 获取配置属性值集合
     * @return 配置属性值集合
     */
    public List<PropertyValue> getPropertyValueList(){
        return this.propertyValueList;
    }

    /**
     * 获取配置属性值集合大小
     * @return 配置属性值集合大小
     */
    public int size(){
        return this.propertyValueList.size();
    }

    /**
     * 添加配置属性值
     * @param pv 配置属性值
     */
    public void addPropertyValue(PropertyValue pv){
        this.propertyValueList.add(pv);
    }

    /**
     * 添加配置属性值
     * @param propertyName 配置属性名字
     * @param propertyValue 配置属性值
     */
    public void addPropertyValue(String propertyName, Object propertyValue){
        this.propertyValueList.add(new PropertyValue(propertyName, propertyValue));
    }

    /**
     * 移除配置属性值
     * @param pv 配置属性值
     */
    public void removePropertyValue(PropertyValue pv){
        this.propertyValueList.remove(pv);
    }

    /**
     * 根据配置属性名字移除配置属性值
     * @param propertyName 配置属性名字
     */
    public void removePropertyValue(String propertyName){
        this.propertyValueList.remove(getPropertyValue(propertyName));
    }

    /**
     * 获取配置属性值集合对象
     * @return 配置属性值集合对象
     */
    public PropertyValue[] getPropertyValues(){
        return this.propertyValueList.toArray(new PropertyValue[this.propertyValueList.size()]);
    }

    /**
     * 根据配置属性名字获取配置属性值对象
     * @param propertyName 配置属性名字
     * @return 配置属性值对象
     */
    public PropertyValue getPropertyValue(String propertyName){
        for (PropertyValue pv : this.propertyValueList) {
            if (pv.getName().equals(propertyName)){
                return pv;
            }
        }
        return null;
    }

    /**
     * 根据配置属性名字获取其值
     * @param propertyName 配置属性名字
     * @return 配置属性的值
     */
    public Object get(String propertyName){
        PropertyValue pv = getPropertyValue(propertyName);
        return pv != null ? pv.getValue() : null;
    }

    /**
     * 根据配置属性名字判断是否存在
     * @param propertyName 配置属性名字
     * @return 存在返回true,不存在返回false
     */
    public boolean contains(String propertyName){
        return getPropertyValue(propertyName) != null;
    }

    /**
     * 判断配置属性值集合是否为空
     * @return 为空返回true,否则返回false
     */
    public boolean isEmpty(){
        return this.propertyValueList.isEmpty();
    }
}
