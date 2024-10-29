package com.codinghub.miniSpring.test.service;



import com.codinghub.miniSpring.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class MyAfterAdvice implements AfterReturningAdvice {
	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Exception {
		System.out.println("----------my interceptor after method call----------");
	}

}
