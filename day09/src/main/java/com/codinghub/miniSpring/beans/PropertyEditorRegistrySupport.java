package com.codinghub.miniSpring.beans;

import com.codinghub.miniSpring.CustomNumberEditor;
import com.codinghub.miniSpring.PropertyEditor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 莱特0905
 * @Description: 属性编辑器注册支持类
 * @Date: 2024/09/29 16:37:44
 */
public class PropertyEditorRegistrySupport {
    /**
     * 默认编辑器
     */
    private Map<Class<?>, PropertyEditor> defaultEditors;

    /**
     * 自定义编辑器
     */
    private Map<Class<?>, PropertyEditor> customEditors;

    public PropertyEditorRegistrySupport(){
        registerDefaultEditors();
    }

    /**
     * 注册默认编辑器
     */
    protected void registerDefaultEditors(){
        createDefaultEditors();
    }

    /**
     * 创建默认编辑器
     */
    private void createDefaultEditors(){
        this.defaultEditors = new HashMap<>(64);

        // 集合编辑器的默认实例。
        this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, false));
        this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
        this.defaultEditors.put(long.class, new CustomNumberEditor(Long.class, false));
        this.defaultEditors.put(Long.class, new CustomNumberEditor(Long.class, true));
        this.defaultEditors.put(float.class, new CustomNumberEditor(Float.class, false));
        this.defaultEditors.put(Float.class, new CustomNumberEditor(Float.class, true));
        this.defaultEditors.put(double.class, new CustomNumberEditor(Double.class, false));
        this.defaultEditors.put(Double.class, new CustomNumberEditor(Double.class, true));
        this.defaultEditors.put(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
        this.defaultEditors.put(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));

        this.defaultEditors.put(String.class, new StringEditor(String.class, true));
    }

    /**
     * 获取默认编辑器
     * @param requiredType 需要的类型
     * @return 默认编辑器
     */
    public PropertyEditor getDefaultEditor(Class<?> requiredType){
        return this.defaultEditors.get(requiredType);
    }

    /**
     * 注册自定义编辑器
     * @param requiredType
     * @param propertyEditor
     */
    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor){
        if(this.customEditors == null){
            this.customEditors = new LinkedHashMap<>(16);
        }
        this.customEditors.put(requiredType, propertyEditor);
    }

    /**
     * 寻找自定义编辑器
     * @param requiredType 需要的类型
     * @return 自定义编辑器
     */
    public PropertyEditor findCustomEditor( Class<?> requiredType) {
        Class<?> requiredTypeToUse = requiredType;
        return getCustomEditor(requiredTypeToUse);
    }

    /**
     * 获取自定义编辑器
     * @param requiredType 需要的类型
     * @return 自定义编辑器
     */
    public PropertyEditor getCustomEditor( Class<?> requiredType) {
        if (requiredType == null || this.customEditors == null) {
            return null;
        }
        // 检查直接注册的编辑器的类型。
        PropertyEditor editor = this.customEditors.get(requiredType);

        return editor;
    }

    /**
     * 是否有自定义编辑器的元素
     * @param elementType 元素类型
     * @return 存在返回true，否则返回false
     */
    public boolean hasCustomEditorForElement(Class<?> elementType){
        // 没有特定属性的编辑器->检查特定类型的编辑器。
        return (elementType != null && this.customEditors != null && this.customEditors.containsKey(elementType));
    }
}
