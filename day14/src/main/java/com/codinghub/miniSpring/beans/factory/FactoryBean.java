package com.codinghub.miniSpring.beans.factory;

/**
 * @author 莱特0905
 * @Description: 工厂Bean
 * @Date: 2024/10/25 16:53:01
 */
public interface FactoryBean<T> {
    /**
     * 获取Bean实例
     * @return 实例
     * @throws Exception 异常
     */
    T getObject() throws Exception;

    /**
     * 获取实例类型
     * @return 实例Class字节对象
     */
    Class<?> getObjectType();

    /**
     * 是否是单例，默认单例
     * @return true
     */
    default boolean isSingleton(){
        return true;
    }
}
