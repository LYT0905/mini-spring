package com.codinghub.miniSpring.core.env;

/**
 * @author 莱特0905
 * @Description: 获取Environment实例
 * @Date: 2024/09/18 20:45:41
 */
public interface EnvironmentCapable {
    /**
     * 获取环境配置
     * @return 环境配置
     */
    Environment getEnvironment();
}
