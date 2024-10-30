package com.codinghub.miniSpring.aop;

import com.codinghub.miniSpring.utils.PatternMatchUtils;

import java.lang.reflect.Method;

/**
 * @author 莱特0905
 * @Description: 根据名字匹配切点
 * @Date: 2024/10/30 16:58:35
 */
public class NameMatchMethodPointcut implements MethodMatcher, Pointcut{
    /**
     * 需要映射的名字
     */
    private String mappedName = "";

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
    }


    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if(mappedName.equals(method.getName()) || isMatch(method.getName(), mappedName)){
            return true;
        }
        return false;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }

    /**
     * 判断需要映射的方法和此时的方法名字是否相同
     * @param methodName 方法名字
     * @param mappedName 需要映射的方法
     * @return 相同返回true,否则返回false
     */
    protected boolean isMatch(String methodName, String mappedName){
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }
}
