package com.codinghub.miniSpring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 莱特0905
 * @Description: 处理器适配器
 * @Date: 2024/09/26 17:42:47
 */
public interface HandlerAdapter {
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
