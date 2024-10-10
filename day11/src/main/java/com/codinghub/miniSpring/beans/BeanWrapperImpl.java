package com.codinghub.miniSpring.beans;

import com.codinghub.miniSpring.beans.PropertyEditor;
import com.codinghub.miniSpring.beans.AbstractPropertyAccessor;
import com.codinghub.miniSpring.beans.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 莱特0905
 * @Description: Bean包装实现类
 * @Date: 2024/09/29 17:14:04
 */
public class BeanWrapperImpl extends AbstractPropertyAccessor {

    /**
     * 经过包装的对象
     */
    Object wrappedObject;

    /**
     * 类
     */
    Class<?> clz;

    public BeanWrapperImpl(Object object) {
        super();
        this.wrappedObject = object;
        this.clz = object.getClass();
    }

    @Override
    public void setPropertyValue(PropertyValue pv) {
        BeanPropertyHandler propertyHandler = new BeanPropertyHandler(pv.getName());
        PropertyEditor pe = this.getCustomEditor(propertyHandler.getPropertyClz());
        if (pe == null){
            pe = this.getDefaultEditor(propertyHandler.getPropertyClz());
        }
        if (pe != null){
            pe.setAsText((String) pv.getValue());
            propertyHandler.setValue(pe.getValue());
        }else {
            propertyHandler.setValue(pv.getValue());
        }
    }

    /**
     * Bean属性处理内部类
     */
    class BeanPropertyHandler{
        /**
         * 写方法
         */
        Method writeMethod = null;

        /**
         * 读方法
         */
        Method readMethod = null;

        /**
         * 属性类
         */
        Class<?> propertyClz = null;

        /**
         * 获取属性类
         * @return 属性类
         */
        public Class<?> getPropertyClz() {
            return propertyClz;
        }


        public BeanPropertyHandler(String propertyName) {
            try {
                Field field = clz.getDeclaredField(propertyName);
                propertyClz = field.getType();
                this.writeMethod = clz.getDeclaredMethod("set"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1),propertyClz);
                this.readMethod = clz.getDeclaredMethod("get"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        /**
         * 获取值
         * @return 值
         */
        public Object getValue(){
            Object result = null;
            writeMethod.setAccessible(true);

            try {
                result =  readMethod.invoke(wrappedObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return result;
        }

        /**
         * 设置值
         * @param value 值
         */
        public void setValue(Object value){
            writeMethod.setAccessible(true);
            try {
                writeMethod.invoke(wrappedObject, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
