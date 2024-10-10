package com.codinghub.miniSpring.web.servlet;

import com.codinghub.miniSpring.beans.BeansException;
import com.codinghub.miniSpring.web.context.support.AnnotationConfigWebApplicationContext;
import com.codinghub.miniSpring.web.context.WebApplicationContext;
import com.codinghub.miniSpring.web.method.HandlerMethod;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author 莱特0905
 * @Description: 请求地址控制器
 * @Date: 2024/09/23 18:10:13
 */
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * 把Listener启动的上下文和DispatcherServlet启动的上下文两者区分开来。
     * 按照时序关系，Listener启动在前，
     * 对应的上下文我们把它叫作parentApplicationContext。
     */
    private WebApplicationContext webApplicationContext;
    private WebApplicationContext parentWebApplicationContext;
    private String sContextConfigLocation;
    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";
    public static final String HANDLER_MAPPING_BEAN_NAME = "handlerMapping";
    public static final String HANDLER_ADAPTER_BEAN_NAME = "handlerAdapter";
    public static final String MULTIPART_RESOLVER_BEAN_NAME = "multipartResolver";
    public static final String LOCALE_RESOLVER_BEAN_NAME = "localeResolver";
    public static final String HANDLER_EXCEPTION_RESOLVER_BEAN_NAME = "handlerExceptionResolver";
    public static final String REQUEST_TO_VIEW_NAME_TRANSLATOR_BEAN_NAME = "viewNameTranslator";
    public static final String VIEW_RESOLVER_BEAN_NAME = "viewResolver";
    private static final String DEFAULT_STRATEGIES_PATH = "DispatcherServlet.properties";
    private static final Properties defaultStrategies = null;
    // 存储controller的名称与对象的映射关系
    private Map<String, Object> controllerObjs = new HashMap<>();
    // 存储controller的名称数组列表
    private List<String> controllerNames = new ArrayList<>();
    // 存储controller名称与类的映射关系
    private Map<String, Class<?>> controllerClasses = new HashMap<>();
    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;
    private ViewResolver viewResolver;

    public DispatcherServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.parentWebApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        sContextConfigLocation = config.getInitParameter("contextConfigLocation");


        this.webApplicationContext = new AnnotationConfigWebApplicationContext(this.sContextConfigLocation, this.parentWebApplicationContext);

        Refresh();
    }

    /**
     * 刷新动作
     */
    protected void Refresh() {
        initHandlerMappings(this.webApplicationContext);
        initHandlerAdapters(this.webApplicationContext);
        initViewResolvers(this.webApplicationContext);
    }

    /**
     * 初始化处理映射关系处理器
     *
     * @param wac Web应用上下文
     */
    protected void initHandlerMappings(WebApplicationContext wac) {
        try {
            this.handlerMapping = (HandlerMapping) wac.getBean(HANDLER_MAPPING_BEAN_NAME);
        } catch (BeansException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初始化处理器适配器
     *
     * @param wac Web应用上下文
     */
    protected void initHandlerAdapters(WebApplicationContext wac) {
        try {
            this.handlerAdapter = (HandlerAdapter) wac.getBean(HANDLER_ADAPTER_BEAN_NAME);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化视图解析器
     *
     * @param wac Web应用上下文
     */
    protected void initViewResolvers(WebApplicationContext wac) {
        try {
            this.viewResolver = (ViewResolver) wac.getBean(VIEW_RESOLVER_BEAN_NAME);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }


    /**
     * service层
     *
     * @param request  请求
     * @param response 响应
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);
        try {
            doDispatch(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 处理请求
     *
     * @param request  请求
     * @param response 响应
     * @throws Exception 自定义异常
     */
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpServletRequest processedRequest = request;
        HandlerMethod handlerMethod = null;
        ModelAndView mv = null;
        handlerMethod = this.handlerMapping.getHandler(processedRequest);
        if (handlerMethod == null) {
            return;
        }
        HandlerAdapter ha = this.handlerAdapter;
        mv = ha.handle(request, response, handlerMethod);

        render(processedRequest, response, mv);
    }

    /**
     * 渲染视图
     *
     * @param request  请求
     * @param response 响应
     * @param mv       视图与模型
     * @throws Exception 异常
     */
    protected void render(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {
        if (mv == null) {
            response.getWriter().flush();
            response.getWriter().close();
            return;
        }

        String sTarget = mv.getViewName();
        Map<String, Object> modelMap = mv.getModel();
        View view = resolveViewName(sTarget, modelMap, request);
        if (view == null){
            return;
        }

        view.render(modelMap, request, response);
    }

    /**
     * 处理视图名字
     *
     * @param viewName 视图名字
     * @param model    模型
     * @param request  请求
     * @return 视图
     * @throws Exception 异常
     */
    protected View resolveViewName(String viewName, Map<String, Object> model, HttpServletRequest request) throws Exception {
        if(this.viewResolver != null){
            View view = viewResolver.resolveViewName(viewName);
            if (view != null){
                return view;
            }
        }
        return null;
    }
}
