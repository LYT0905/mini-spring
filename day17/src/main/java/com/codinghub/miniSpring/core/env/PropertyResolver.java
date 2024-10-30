package com.codinghub.miniSpring.core.env;

/**
 * @author 莱特0905
 * @Description: 属性解析器
 * @Date: 2024/09/18 20:46:07
 */
public interface PropertyResolver {
    /**
     * 是否包含属性
     * @param key 属性的键
     * @return 包含返回true,否则返回false
     */
    boolean containsProperty(String key);

    /**
     * 获取属性
     * @param key 属性的键
     * @return 属性
     */
   String getProperty(String key);

    /**
     * 获取属性
     * @param key 属性的键
     * @param defaultValue 默认的值
     * @return 属性
     */
   String getProperty(String key, String defaultValue);

    /**
     * 获取属性
     * @param key 属性的键
     * @param targetType 目标类型
     * @return 属性
     * @param <T> 泛型
     */
   <T> T getProperty(String key, Class<T> targetType);

    /**
     * 获取属性
     * @param key 属性的键
     * @param targetType 目标类型
     * @param defaultValue 默认的值
     * @return 属性
     * @param <T> 泛型
     */
   <T> T getProperty(String key, Class<T> targetType, T defaultValue);

    /**
     * 获取属性的类
     * @param key 属性的键
     * @param targetType 目标类型
     * @return 属性
     * @param <T> 泛型
     */
   <T> Class<T> getPropertyAsClass(String key, Class<T> targetType);

    /**
     * 获取需要的属性
     * @param key 属性的键
     * @return 需要的属性
     * @throws IllegalStateException 非法状态错误
     */
   String getRequiredProperty(String key) throws IllegalStateException;

    /**
     * 获取需要的属性
     * @param key 属性的键
     * @param targetType 目标类型
     * @return 需要的属性
     * @throws IllegalStateException 非法状态错误
     */
    <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException;

    /**
     * 解决占位符
     * @param text 文本
     * @return 解决占位符的文本
     */
    String resolvePlaceholders(String text);

    /**
     * 解析所需占位符
     * @param text 文本
     * @return 解析所需占位符的文本
     * @exception IllegalArgumentException 非法参数异常
     */
    String resolveRequiredPlaceholders(String text) throws IllegalArgumentException;
}
