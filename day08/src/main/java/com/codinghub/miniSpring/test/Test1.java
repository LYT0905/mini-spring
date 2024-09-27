package com.codinghub.miniSpring.test;



import com.codinghub.miniSpring.beans.BeansException;
import com.codinghub.miniSpring.context.ClassPathXmlApplicationContext;
import com.codinghub.miniSpring.test.service.BaseService;

public class Test1 {

	public static void main(String[] args) throws BeansException {
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//	    AService aService;
//	    BaseService bService;
//		try {
//			aService = (AService)ctx.getBean("aservice");
//		    aService.sayHello();
//
//		    bService = (BaseService)ctx.getBean("baseservice");
//		    bService.sayHello();
//		} catch (BeansException e) {
//			e.printStackTrace();
//		}

		ClassPathXmlApplicationContext classPathXmlApplicationContext =
				new ClassPathXmlApplicationContext("applicationContext.xml");
		BaseService aservice =(BaseService) classPathXmlApplicationContext.getBean("baseservice");
		aservice.sayHello();
	}

}
