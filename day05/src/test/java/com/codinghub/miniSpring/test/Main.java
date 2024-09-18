package com.codinghub.miniSpring.test;

import com.codinghub.miniSpring.context.ClassPathXmlApplicationContext;

/**
 * @author 莱特0905
 * @Description: 测试程序主启动类
 * @Date: 2024/09/09 16:02:58
 */
public class Main {
    public static void main(String[] args) throws Exception{
        ClassPathXmlApplicationContext classPathXmlApplicationContext =
                new ClassPathXmlApplicationContext("BeandefinitionXml.xml");
        BaseService aservice =(BaseService) classPathXmlApplicationContext.getBean("baseservice");
        aservice.sayHello();
    }
}
