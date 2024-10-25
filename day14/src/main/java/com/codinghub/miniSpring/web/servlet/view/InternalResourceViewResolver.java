package com.codinghub.miniSpring.web.servlet.view;

import com.codinghub.miniSpring.web.servlet.View;
import com.codinghub.miniSpring.web.servlet.ViewResolver;

/**
 * @author 莱特0905
 * @Description: 内部资源视图解析器
 * @Date: 2024/10/02 19:23:22
 */
public class InternalResourceViewResolver implements ViewResolver {

    // 视图类
    private Class<?> viewClass = null;
    // 视图类名
    private String viewClassName = "";
    // 前缀
    private String prefix = "";
    // 后缀
    private String suffix = "";
    // 内容类型
    private String contentType;

    public InternalResourceViewResolver() {
        if(getViewClass() == null){
            setViewClass(JstlView.class);
        }
    }

    public void setViewClassName(String viewClassName) {
        this.viewClassName = viewClassName;
        Class<?> clz = null;
        try {
            clz = Class.forName(viewClassName);
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        setViewClass(clz);
    }

    /**
     * 处理视图名字
     * @param viewName 视图名字
     * @return 视图
     * @throws Exception 异常
     */
    @Override
    public View resolveViewName(String viewName) throws Exception {
        return buildView(viewName);
    }

    protected View buildView(String viewName) throws Exception{
        Class<?> viewClass = getViewClass();

        View view = (View) viewClass.newInstance();
        view.setUrl(getPrefix() + viewName + getSuffix());

        String contentType = getContentType();
        view.setContentType(contentType);

        return view;
    }

    protected String getViewClassName() {
        return this.viewClassName;
    }
    public void setViewClass(Class<?> viewClass) {
        this.viewClass = viewClass;
    }
    protected Class<?> getViewClass() {
        return this.viewClass;
    }
    public void setPrefix(String prefix) {
        this.prefix = (prefix != null ? prefix : "");
    }
    protected String getPrefix() {
        return this.prefix;
    }
    public void setSuffix(String suffix) {
        this.suffix = (suffix != null ? suffix : "");
    }
    protected String getSuffix() {
        return this.suffix;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    protected String getContentType() {
        return this.contentType;
    }
}
