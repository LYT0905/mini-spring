package com.codinghub.miniSpring.web.servlet;

import com.codinghub.miniSpring.beans.BeansException;
import com.codinghub.miniSpring.beans.factory.annotation.Autowired;
import com.codinghub.miniSpring.web.AnnotationConfigWebApplication;
import com.codinghub.miniSpring.web.RequestMapping;
import com.codinghub.miniSpring.web.WebApplicationContext;
import com.codinghub.miniSpring.web.XmlScanComponentHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
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
    // 存放需要扫描的包名
    private List<String> packageNames = new ArrayList<>();
    // 存储controller的名称与对象的映射关系
    private Map<String,Object> controllerObjs = new HashMap<>();
    // 存储controller的名称数组列表
    private List<String> controllerNames = new ArrayList<>();
    // 存储controller名称与类的映射关系
    private Map<String,Class<?>> controllerClasses = new HashMap<>();
    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;

    public DispatcherServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);

        this.parentWebApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        sContextConfigLocation = config.getInitParameter("contextConfigLocation");

        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        }catch (MalformedURLException ex){
            ex.printStackTrace();
        }
        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);

        this.webApplicationContext = new AnnotationConfigWebApplication(sContextConfigLocation, this.parentWebApplicationContext);

        Refresh();
    }

    /**
     * 刷新动作
     */
    protected void Refresh(){
        initController();

        initHandlerMappings(this.webApplicationContext);
        initHandlerAdapters(this.webApplicationContext);
        initViewResolvers(this.webApplicationContext);
    }

    /**
     * 初始化控制层
     */
    protected void initController(){
        this.controllerNames = Arrays.asList(this.webApplicationContext.getBeanDefinitionNames());

        for (String controllerName : this.controllerNames) {
            Object obj = null;
            Class<?> clz = null;
            try {
                clz = Class.forName(controllerName);
                boolean isInterface = clz.isInterface();
                if (isInterface){
                    continue;
                }
                this.controllerClasses.put(controllerName, clz);
                this.controllerObjs.put(controllerName, this.webApplicationContext.getBean(controllerName));
                System.out.println("controller : "+controllerName);
            }catch (ClassNotFoundException ex){
                ex.printStackTrace();
            }catch (BeansException ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * 初始化处理映射关系处理器
     * @param wac Web应用上下文
     */
    protected void initHandlerMappings(WebApplicationContext wac){
        this.handlerMapping = new RequestMappingHandlerMapping(wac);
    }

    /**
     * 初始化处理器适配器
     * @param wac Web应用上下文
     */
    protected void initHandlerAdapters(WebApplicationContext wac){
        this.handlerAdapter = new RequestMappingHandlerAdapter(wac);
    }

    /**
     * 初始化视图解析器
     * @param wac Web应用上下文
     */
    protected void initViewResolvers(WebApplicationContext wac){
    }


    /**
     * service层
     * @param request 请求
     * @param response 响应
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);
        try {
            doDispatch(request, response);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 处理请求
     * @param request 请求
     * @param response 响应
     * @throws Exception 自定义异常
     */
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpServletRequest processedRequest = request;
        HandlerMethod handlerMethod = null;
        handlerMethod = this.handlerMapping.getHandler(processedRequest);
        if (handlerMethod == null){
            return;
        }
        HandlerAdapter ha = this.handlerAdapter;
        ha.handle(request, response, handlerMethod);
    }
}
