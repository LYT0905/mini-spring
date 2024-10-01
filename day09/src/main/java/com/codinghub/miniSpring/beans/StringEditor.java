package com.codinghub.miniSpring.beans;

import com.codinghub.miniSpring.PropertyEditor;

/**
 * @author 莱特0905
 * @Description: 字符串编辑器
 * @Date: 2024/09/29 17:00:01
 */
public class StringEditor implements PropertyEditor {

    /**
     * 字节类型
     */
    private Class<String> strClass;

    /**
     * 字符串解析
     */
    private String strFormat;

    /**
     * 是否允许为空
     */
    private boolean allowEmpty;

    /**
     * 值
     */
    private Object value;


    public StringEditor(Class<String> strClass, boolean allowEmpty) {
        this(strClass, "", allowEmpty);
    }

    public StringEditor(Class<String> strClass, String strFormat, boolean allowEmpty) {
        this.strClass = strClass;
        this.strFormat = strFormat;
        this.allowEmpty = allowEmpty;
    }

    /**
     * 作为文本格式进行设置
     * @param text 文本
     */
    @Override
    public void setAsText(String text) {
        setValue(text);
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public String getAsText() {
        return value.toString();
    }
}
