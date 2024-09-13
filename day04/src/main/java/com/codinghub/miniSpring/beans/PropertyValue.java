package com.codinghub.miniSpring.beans;

/**
 * @author 莱特0905
 * @Description: 依赖注入实体对象
 * @Date: 2024/09/12 14:14:20
 */
public class PropertyValue {
    private final String type;
    private final String name;
    private final Object value;
    private final boolean isRef;

    public PropertyValue(String type, String name, Object value, boolean isRef) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.isRef = isRef;
    }

    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public boolean isRef() {
        return isRef;
    }
}
