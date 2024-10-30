package com.codinghub.miniSpring.test.service;



import com.codinghub.miniSpring.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class MyBeforeAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) throws Exception {
		System.out.println("----------my interceptor befor method call----------");
	}

}
