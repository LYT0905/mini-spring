package com.codinghub.miniSpring.test;


import com.codinghub.miniSpring.common.exception.BeansException;
import com.codinghub.miniSpring.context.ClassPathXmlApplicationContext;

public class Test1 {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	    AService aService;
	    BaseService bService;
		try {
			aService = (AService)ctx.getBean("aservice");
		    aService.sayHello();
		    
		    bService = (BaseService)ctx.getBean("baseservice");
		    bService.sayHello();
		} catch (BeansException e) {
			e.printStackTrace();
		}
	}

}
