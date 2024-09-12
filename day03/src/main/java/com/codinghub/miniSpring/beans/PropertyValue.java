package com.codinghub.miniSpring.beans;

/**
 * @author 莱特0905
 * @Description: 依赖注入实体对象
 * @Date: 2024/09/12 14:14:20
 */
public class PropertyValue {
    private final String name;
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }


}
