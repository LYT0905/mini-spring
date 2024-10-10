package com.codinghub.miniSpring.core.env;

/**
 * @author 莱特0905
 * @Description: 获取属性
 * @Date: 2024/09/18 20:45:09
 */
public interface Environment extends PropertyResolver{
    /**
     * 获取活动配置文件
     * @return 活动配置文件集合
     */
    String[] getActiveProfiles();

    /**
     * 获取默认配置文件
     * @return 默认配置文件
     */
    String[] getDefaultProfiles();

    /**
     * 接收文件
     * @param profiles 文件
     * @return 接受成功返回true，否则返回false
     */
    boolean acceptsProfiles(String... profiles);
}
