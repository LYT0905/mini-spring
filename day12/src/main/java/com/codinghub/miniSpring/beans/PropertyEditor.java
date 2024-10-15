package com.codinghub.miniSpring.beans;

/**
 * @author 莱特0905
 * @Description: 属性编辑器
 * @Date: 2024/09/29 16:38:35
 */
public interface PropertyEditor {
    /**
     * 作为文本格式进行设置
     * @param text 文本
     */
    void setAsText(String text);

    /**
     * 设置值
     * @param value 值
     */
    void setValue(Object value);

    /**
     * 获取值
     * @return 值
     */
    Object getValue();

    /**
     * 获取作为文本的值
     * @return 作为文本的值
     */
    String getAsText();
}
