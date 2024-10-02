package com.codinghub.miniSpring.web.servlet;

import com.codinghub.miniSpring.beans.BeansException;
import com.codinghub.miniSpring.web.WebApplicationContext;
import com.codinghub.miniSpring.web.WebBindingInitializer;
import com.codinghub.miniSpring.web.WebDataBinder;
import com.codinghub.miniSpring.web.WebDataBinderFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author 莱特0905
 * @Description: 请求处理适配器
 * @Date: 2024/09/26 17:55:28
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter{
    WebApplicationContext wac = null;
    private WebBindingInitializer webBindingInitializer = null;

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
        try {
            this.webBindingInitializer = (WebBindingInitializer) this.wac.getBean("webBindingInitializer");
        } catch (BeansException e) {
            e.printStackTrace();
        }
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
     * @return ModelAndView 模型和视图对象
     */
    private ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler){
        ModelAndView mv = null;

        try {
            invokeHandlerMethod(request, response, handler);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return mv;
    }

    /**
     * 执行处理器方法
     * @param request 请求
     * @param response 响应
     * @param handlerMethod 处理方法
     */
    protected void invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        WebDataBinderFactory binderFactory = new WebDataBinderFactory();
        // 获取方法参数
        Parameter[] methodParameters = handlerMethod.getMethod().getParameters();
        Object[] methodParamObjs = new Object[methodParameters.length];

        int i = 0;
        for (Parameter methodParameter : methodParameters) {
            // 创建对应方法参数类型实例
            Object methodParamObj = methodParameter.getType().newInstance();
            // 创建Web数据绑定对象
            WebDataBinder wdb = binderFactory.createBinder(request, methodParamObj, methodParameter.getName());
            // 初始化绑定对象
            webBindingInitializer.initBinder(wdb);
            // 将请求参数与其进行绑定
            wdb.bind(request);
            // 将对应方法参数类型实例存入数组
            methodParamObjs[i] = methodParamObj;
            i++;
        }

        Method invocableMethod = handlerMethod.getMethod();
        Object returnobj = invocableMethod.invoke(handlerMethod.getBean(), methodParamObjs);

        response.getWriter().append(returnobj.toString());
    }

    /**
     * 获取Web绑定初始化对象
     * @return  Web绑定初始化对象
     */
    public WebBindingInitializer getWebBindingInitializer() {
        return webBindingInitializer;
    }

    /**
     * 设置 Web绑定初始化对象
     * @param webBindingInitializer  Web绑定初始化对象
     */
    public void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }
}
