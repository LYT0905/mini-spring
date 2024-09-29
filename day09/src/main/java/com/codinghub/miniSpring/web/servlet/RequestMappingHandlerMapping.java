package com.codinghub.miniSpring.web.servlet;

import com.codinghub.miniSpring.beans.BeansException;
import com.codinghub.miniSpring.web.RequestMapping;
import com.codinghub.miniSpring.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author 莱特0905
 * @Description: 请求映射处理
 * @Date: 2024/09/26 17:48:02
 */
public class RequestMappingHandlerMapping implements HandlerMapping{
    WebApplicationContext wac;
    private final MappingRegistry mappingRegistry = new MappingRegistry();

    public RequestMappingHandlerMapping(WebApplicationContext wac){
        this.wac = wac;

        initMapping();
    }


    /**
     * 初始化Mapping
     */
    protected void initMapping(){
        Class<?> clz = null;
        Object obj = null;
        String[] controllerNames = this.wac.getBeanDefinitionNames();
        for (String controllerName : controllerNames) {
            try {
                clz = Class.forName(controllerName);
                if (clz == null){
                    continue;
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
            try {
                obj = this.wac.getBean(controllerName);
            }catch (BeansException ex){
                ex.printStackTrace();
            }
            Method[] methods = clz.getDeclaredMethods();
            if (methods != null){
                for (Method method : methods) {
                    boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                    if (isRequestMapping){
                        String methodName = method.getName();
                        String urlmapping = method.getAnnotation(RequestMapping.class).value();
                        this.mappingRegistry.getUrlMappingNames().add(urlmapping);
                        this.mappingRegistry.getMappingObjs().put(urlmapping, obj);
                        this.mappingRegistry.getMappingMethods().put(urlmapping, method);
                    }
                }
            }
        }
    }

    /**
     * 获取处理对象
     * @param request 请求
     * @return 处理对象
     * @throws Exception 自定义异常
     */
    @Override
    public HandlerMethod getHandler(HttpServletRequest request) throws Exception {
        String servletPath = request.getServletPath();
        if (!this.mappingRegistry.getUrlMappingNames().contains(servletPath)){
            return null;
        }

        Method method = this.mappingRegistry.getMappingMethods().get(servletPath);
        Object obj = this.mappingRegistry.getMappingObjs().get(servletPath);
        HandlerMethod handlerMethod = new HandlerMethod(method, obj);
        return handlerMethod;
    }
}
