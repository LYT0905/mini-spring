package com.codinghub.miniSpring.web;

import com.codinghub.miniSpring.PropertyEditor;
import com.codinghub.miniSpring.beans.AbstractPropertyAccessor;
import com.codinghub.miniSpring.beans.PropertyValues;
import com.codinghub.miniSpring.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 莱特0905
 * @Description: 传入数据的绑定
 * @Date: 2024/09/29 16:32:37
 */
public class WebDataBinder {
    /**
     * 目标绑定类型
     */
    private Object target;

    /**
     * 目标绑定字节类型
     */
    private Class<?> clz;

    /**
     * 目标类型名字
     */
    private String objectName;

    /**
     * 抽象属性访问器
     */
    AbstractPropertyAccessor propertyAccessor;

    public WebDataBinder(Object target){
        this(target, "");
    }

    public WebDataBinder(Object target, String targetName){
        this.target = target;
        this.objectName = targetName;
        this.clz = this.target.getClass();
        this.propertyAccessor = new BeanWrapperImpl(this.target);
    }

    /**
     * 绑定
     * @param request 请求
     */
    public void bind(HttpServletRequest request){
        PropertyValues mpvs = assignParameters(request);
        addBindValues(mpvs, request);
        doBind(mpvs);
    }

    /**
     * 分配参数(将Request参数解析成PropertyValues)
     * @param request 请求
     * @return 属性值实体
     */
    private PropertyValues assignParameters(HttpServletRequest request) {
        Map<String,Object> map = WebUtils.getParametersStartingWith(request, "");
        return new PropertyValues(map);
    }

    /**
     * 添加绑定值
     * @param mpvs 属性值实体
     * @param request 请求
     */
    protected void addBindValues(PropertyValues mpvs, HttpServletRequest request) {
    }

    /**
     * 执行绑定操作
     * @param mpvs 属性值实体
     */
    private void doBind(PropertyValues mpvs) {
        applyPropertyValues(mpvs);
    }

    /**
     * 设置属性值(实际将参数值与对象属性进行绑定的方法)
     * @param mpvs 属性值实体
     */
    protected void applyPropertyValues(PropertyValues mpvs) {
        getPropertyAccessor().setPropertyValues(mpvs);
    }

    /**
     * 获取属性处理器
     * @return 属性处理器
     */
    protected AbstractPropertyAccessor getPropertyAccessor() {
        return this.propertyAccessor;
    }

    /**
     * 注册自定义编辑器
     * @param requiredType 需要的类型
     * @param propertyEditor 属性编辑器
     */
    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        getPropertyAccessor().registerCustomEditor(requiredType, propertyEditor);
    }
}
