package com.codinghub.miniSpring.web.servlet;

import com.codinghub.miniSpring.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 莱特0905
 * @Description: 处理映射关系
 * @Date: 2024/09/26 17:36:13
 */
public interface HandlerMapping {
    HandlerMethod getHandler(HttpServletRequest request) throws Exception;
}
