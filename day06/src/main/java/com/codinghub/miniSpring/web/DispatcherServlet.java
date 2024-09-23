package com.codinghub.miniSpring.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
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

    protected void Refresh(){
        initController();
        initMapping();
    }

    protected void initController(){
        this.controllerNames = scanPackages(this.packageNames);

        for (String controllerName : this.controllerNames) {
            Object obj = null;
            Class<?> clz = null;
            try {
                clz = Class.forName(controllerName);
                this.controllerClasses.put(controllerName, clz);
            }catch (ClassNotFoundException ex){
                ex.printStackTrace();
            }
            try {
                obj = clz.newInstance();
                this.controllerObjs.put(controllerName, obj);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> scanPackages(List<String> packages){
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packages) {
            tempControllerNames.addAll(scanPackage(packageName));
        }
        return tempControllerNames;
    }

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

    protected void initMapping(){
        for (String controllerName : this.controllerNames) {
            Class<?> clazz = this.controllerClasses.get(controllerName);
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
        System.out.println(servletPath);
        if (!this.urlMappingNames.contains(servletPath)){
            return;
        }
        Object obj = null;
        Object objResult = null;
        try {
            Method method = this.mappingMethods.get(servletPath);
            obj = this.mappingObjs.get(servletPath);
            objResult = method.invoke(obj);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        response.getWriter().append(objResult.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
