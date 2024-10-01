package com.codinghub.miniSpring;

import com.codinghub.miniSpring.utils.NumberUtils;
import com.codinghub.miniSpring.utils.StringUtils;

import java.text.NumberFormat;

/**
 * @author 莱特0905
 * @Description: 自定义数字编辑器
 * @Date: 2024/09/29 16:46:58
 */
public class CustomNumberEditor implements PropertyEditor{
    /**
     * 数字的字节类型
     */
    private Class<? extends Number> numberClass;

    /**
     * 数字格式化
     */
    private NumberFormat numberFormat;

    /**
     * 是否允许为空
     */
    private boolean allowEmpty;

    /**
     * 值
     */
    private Object value;


    public CustomNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty) {
        this(numberClass, null, allowEmpty);
    }

    public CustomNumberEditor(Class<? extends Number> numberClass, NumberFormat numberFormat, boolean allowEmpty) {
        this.numberClass = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }

    /**
     * 作为文本格式进行设置
     * @param text 文本
     */
    @Override
    public void setAsText(String text) {
        if(this.allowEmpty && !StringUtils.hasText(text)){
            // 将空字符串视为空值。
            setValue(null);
        } else if (this.numberFormat != null) {
            // 使用给定的NumberFormat解析文本。
            setValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
        }else {
            // 使用默认的valueOf方法解析文本。
            setValue(NumberUtils.parseNumber(text, this.numberClass));
        }
    }

    /**
     * 设置值
     * @param value 值
     */
    @Override
    public void setValue(Object value) {
        if(value instanceof Number){
            this.value = (NumberUtils.convertNumberToTargetClass((Number) value, this.numberClass));
        }else {
            this.value = value;
        }
    }

    /**
     * 获取值
     * @return 值
     */
    @Override
    public Object getValue() {
        return this.value;
    }

    /**
     * 获取作为文本的值
     * @return 作为文本的值
     */
    @Override
    public String getAsText() {
        Object value = this.value;
        if (value == null){
            return "";
        }
        if (this.numberFormat != null){
            // 使用NumberFormat作为渲染值。
            return this.numberFormat.format(value);
        }else {
            // 使用toString方法来呈现值。
            return value.toString();
        }
    }
}
