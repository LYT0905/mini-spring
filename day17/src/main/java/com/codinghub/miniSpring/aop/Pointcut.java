package com.codinghub.miniSpring.aop;

/**
 * @author 莱特0905
 * @Description: 切点类
 * @Date: 2024/10/30 17:02:36
 */
public interface Pointcut {

    /**
     * 获取此时的方法匹配对象
     * @return 方法匹配对象
     */
    MethodMatcher getMethodMatcher();
}
