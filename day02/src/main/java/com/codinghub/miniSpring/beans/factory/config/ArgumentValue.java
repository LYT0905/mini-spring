package com.codinghub.miniSpring.beans.factory.config;

/**
 * @author 莱特0905
 * @Description: 依赖注入参数实体对象
 * @Date: 2024/09/12 14:11:27
 */
public class ArgumentValue {
    private String name;
    private String type;
    private Object value;

    public ArgumentValue() {
    }

    public ArgumentValue(String name, String type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public ArgumentValue(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
