package com.codinghub.miniSpring.web.servlet;

import com.codinghub.miniSpring.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 莱特0905
 * @Description: 请求处理适配器
 * @Date: 2024/09/26 17:55:28
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter{
    WebApplicationContext wac;

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
    }


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        handleInternal(request, response, (HandlerMethod) handler);
    }

    /**
     * 内部处理请求
     * @param request 请求
     * @param response 响应
     * @param handler 处理器
     */
    private void handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler){
        Method method = handler.getMethod();
        Object obj = handler.getBean();
        Object objResult = null;
        try {
            objResult = method.invoke(obj);
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        try {
            response.getWriter().append(objResult.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
