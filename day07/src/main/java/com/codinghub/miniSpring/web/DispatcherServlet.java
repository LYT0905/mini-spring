package com.codinghub.miniSpring.web;

import com.codinghub.miniSpring.beans.BeansException;
import com.codinghub.miniSpring.beans.factory.annotation.Autowired;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 莱特0905
 * @Description: 请求地址控制器
 * @Date: 2024/09/23 18:10:13
 */
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private WebApplicationContext webApplicationContext;
    private String sContextConfigLocation;
    // 存放需要扫描的包名
    private List<String> packageNames = new ArrayList<>();
    // 存储controller的名称与对象的映射关系
    private Map<String,Object> controllerObjs = new HashMap<>();
    // 存储controller的名称数组列表
    private List<String> controllerNames = new ArrayList<>();
    // 存储controller名称与类的映射关系
    private Map<String,Class<?>> controllerClasses = new HashMap<>();
    // 保存自定义@RequestMapping 名称(URL的名称)的列表
    private List<String> urlMappingNames = new ArrayList<>();
    // 保存URL名称与对象的映射关系
    private Map<String,Object> mappingObjs = new HashMap<>();
    // 保存URL名称与方法的映射关系
    private Map<String,Method> mappingMethods = new HashMap<>();

    public DispatcherServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);

        this.webApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        sContextConfigLocation = config.getInitParameter("contextConfigLocation");

        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        }catch (MalformedURLException ex){
            ex.printStackTrace();
        }
        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);

        Refresh();
    }

    /**
     * 刷新动作
     */
    protected void Refresh(){
        initController();
        initMapping();
    }

    /**
     * 初始化控制层
     */
    protected void initController(){
        this.controllerNames = scanPackages(this.packageNames);

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
            }catch (ClassNotFoundException ex){
                ex.printStackTrace();
            }
            try {
                obj = clz.newInstance();

                populateBean(obj, controllerName);

                this.controllerObjs.put(controllerName, obj);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }catch (BeansException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理Bean对象，进行自动注入
     * @param bean Bean对象
     * @param beanName Bean名字
     * @return Bean
     * @throws BeansException 自定义异常
     */
    protected  Object populateBean(Object bean, String beanName) throws BeansException{
        Object result = bean;

        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null){
            for (Field field : fields) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if (isAutowired){
                    String fieldName = field.getName();
                    Object autowiredObj = this.webApplicationContext.getBean(fieldName);
                    try {
                        field.setAccessible(true);
                        field.set(bean, autowiredObj);
                    }catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }


    /**
     * 扫描定义的包
     * @param packages 定义的包
     * @return 包下面的所有文件
     */
    private List<String> scanPackages(List<String> packages){
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packages) {
            tempControllerNames.addAll(scanPackage(packageName));
        }
        return tempControllerNames;
    }

    /**
     * 扫描单个包
     * @param packageName 包名
     * @return 包下面的所有文件
     */
    private List<String> scanPackage(String packageName){
        List<String> tempControllerNames = new ArrayList<>();
        URL url = this.getClass().getClassLoader().getResource( packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()){
                scanPackage(packageName +"." + file.getName());
            }else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        return tempControllerNames;
    }

    /**
     * 初始化Mapping
     */
    protected void initMapping(){
        for (String controllerName : this.controllerNames) {
            Class<?> clazz = this.controllerClasses.get(controllerName);
            if (clazz == null){
                continue;
            }
            Object obj = this.controllerObjs.get(controllerName);
            Method[] methods = clazz.getDeclaredMethods();
            if (methods != null){
                for (Method method : methods) {
                    boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                    if (isRequestMapping){
                        String methodName = method.getName();
                        String urlmapping = method.getAnnotation(RequestMapping.class).value();
                        this.urlMappingNames.add(urlmapping);
                        this.mappingObjs.put(urlmapping, obj);
                        this.mappingMethods.put(urlmapping, method);
                    }
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if (!this.urlMappingNames.contains(servletPath)){
            return;
        }
        Object obj = null;
        Object objResult = null;
        try {
            Method method = this.mappingMethods.get(servletPath);
            obj = this.mappingObjs.get(servletPath);
            objResult = method.invoke(obj);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        response.getWriter().append(objResult.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
