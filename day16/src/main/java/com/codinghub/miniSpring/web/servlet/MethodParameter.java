package com.codinghub.miniSpring.web.servlet;

/**
 * @author 莱特0905
 * @Description:
 * @Date: 2024/09/26 17:37:45
 */
public class MethodParameter {
    /**
     * 参数类型
     */
    private volatile Class<?> parameterType;

    /**
     * 参数名字
     */
    private volatile String parameterName;

    /**
     * 参数值
     */
    private volatile Object parameterValue;
}
