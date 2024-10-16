package com.codinghub.miniSpring.web.servlet;

/**
 * @author 莱特0905
 * @Description: 视图处理器
 * @Date: 2024/10/02 19:20:23
 */
public interface ViewResolver {
    /**
     * 处理视图名字
     * @param viewName 视图名字
     * @return 视图
     * @throws Exception 异常
     */
    View resolveViewName(String viewName) throws Exception;
}
