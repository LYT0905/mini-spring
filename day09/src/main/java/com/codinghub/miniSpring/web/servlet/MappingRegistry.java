package com.codinghub.miniSpring.web.servlet;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 莱特0905
 * @Description: Mapping注册类(存放对应映射信息)
 * @Date: 2024/09/26 17:43:50
 */
public class MappingRegistry {
    // 保存自定义@RequestMapping 名称(URL的名称)的列表
    private List<String> urlMappingNames = new ArrayList<>();
    // 保存URL名称与对象的映射关系
    private Map<String,Object> mappingObjs = new HashMap<>();
    // 保存URL名称与方法的映射关系
    private Map<String,Method> mappingMethods = new HashMap<>();

    public List<String> getUrlMappingNames() {
        return urlMappingNames;
    }
    public void setUrlMappingNames(List<String> urlMappingNames) {
        this.urlMappingNames = urlMappingNames;
    }
    public Map<String,Object> getMappingObjs() {
        return mappingObjs;
    }
    public void setMappingObjs(Map<String,Object> mappingObjs) {
        this.mappingObjs = mappingObjs;
    }
    public Map<String,Method> getMappingMethods() {
        return mappingMethods;
    }
    public void setMappingMethods(Map<String,Method> mappingMethods) {
        this.mappingMethods = mappingMethods;
    }

}
