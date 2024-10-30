package com.codinghub.miniSpring.web.method.annotation;

import com.codinghub.miniSpring.context.ApplicationContext;
import com.codinghub.miniSpring.context.ApplicationContextAware;
import com.codinghub.miniSpring.http.converter.HttpMessageConverter;
import com.codinghub.miniSpring.web.bind.WebDataBinder;
import com.codinghub.miniSpring.web.bind.annotation.ResponseBody;
import com.codinghub.miniSpring.web.bind.support.WebBindingInitializer;
import com.codinghub.miniSpring.web.bind.support.WebDataBinderFactory;
import com.codinghub.miniSpring.web.method.HandlerMethod;
import com.codinghub.miniSpring.web.servlet.HandlerAdapter;
import com.codinghub.miniSpring.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author 莱特0905
 * @Description: 请求处理适配器
 * @Date: 2024/09/26 17:55:28
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter, ApplicationContextAware {
    private ApplicationContext applicationContext= null;
    private WebBindingInitializer webBindingInitializer = null;
    private HttpMessageConverter messageConverter = null;

    public RequestMappingHandlerAdapter() {
    }


    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return handleInternal(request, response, (HandlerMethod) handler);
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
            mv = invokeHandlerMethod(request, response, handler);
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
    protected ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        WebDataBinderFactory binderFactory = new WebDataBinderFactory();
        // 获取方法参数
        Parameter[] methodParameters = handlerMethod.getMethod().getParameters();
        Object[] methodParamObjs = new Object[methodParameters.length];

        int i = 0;
        for (Parameter methodParameter : methodParameters) {
            if (methodParameter.getType() != HttpServletRequest.class && methodParameter.getType() != HttpServletResponse.class){
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
            } else if (methodParameter.getType() == HttpServletRequest.class) {
                methodParamObjs[i] = request;
            }else if (methodParameter.getType()==HttpServletResponse.class) {
                methodParamObjs[i] = response;
            }
            i++;
        }

        Method invocableMethod = handlerMethod.getMethod();
        Object returnObj = invocableMethod.invoke(handlerMethod.getBean(), methodParamObjs);
        Class<?> returnType = invocableMethod.getReturnType();
        ModelAndView mav = null;

        if (invocableMethod.isAnnotationPresent(ResponseBody.class)){
            this.messageConverter.write(returnObj, response);
        } else if (returnType == void.class) {
        }else {
            if (returnObj instanceof ModelAndView){
                mav = (ModelAndView) returnObj;
            }else if(returnObj instanceof String) {
                String sTarget = (String)returnObj;
                mav = new ModelAndView();
                mav.setViewName(sTarget);
            }
        }


        return mav;
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

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public HttpMessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }
}
