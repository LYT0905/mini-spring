package com.codinghub.miniSpring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 莱特0905
 * @Description: Jdk动态Aop代理
 * @Date: 2024/10/25 16:50:01
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    /**
     * 目标代理对象
     */
    Object target;

    public JdkDynamicAopProxy(Object target) {
        this.target = target;
    }

    /**
     * 获取代理对象
     * @return 代理对象
     */
    @Override
    public Object getProxy() {
        System.out.println("----------Proxy new psroxy instance for  ---------"+target);
        System.out.println("----------Proxy new psroxy instance  classloader ---------"+JdkDynamicAopProxy.class.getClassLoader());
        System.out.println("----------Proxy new psroxy instance  interfaces  ---------"+target.getClass().getInterfaces());

        Object obj = Proxy.newProxyInstance(JdkDynamicAopProxy.class.getClassLoader(), target.getClass().getInterfaces(), this);
        System.out.println("----------Proxy new psroxy instance created r ---------"+obj);
        return obj;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("doAction")) {
            System.out.println("-----before call real object, dynamic proxy........");
            return method.invoke(target, args);
        }
        return null;
    }
}
