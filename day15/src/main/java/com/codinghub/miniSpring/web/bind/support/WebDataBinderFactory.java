package com.codinghub.miniSpring.web.bind.support;

import com.codinghub.miniSpring.web.bind.WebDataBinder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 莱特0905
 * @Description: Web数据绑定工厂
 * @Date: 2024/09/29 17:33:21
 */
public class WebDataBinderFactory {

    /**
     * 创建绑定
     * @param request 请求
     * @param target 目标对象
     * @param objectName 对象名字
     * @return Web数据绑定对象
     */
    public WebDataBinder createBinder(HttpServletRequest request, Object target, String objectName){
        WebDataBinder wbd = new WebDataBinder(target, objectName);
        initBinder(wbd, request);
        return wbd;
    }

    /**
     * 初始化绑定对象
     * @param dataBinder 数据绑定对象
     * @param request 请求
     */
    protected void initBinder(WebDataBinder dataBinder, HttpServletRequest request){
    }
}
