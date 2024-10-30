package com.codinghub.miniSpring.test.service;


import com.codinghub.miniSpring.aop.MethodInterceptor;
import com.codinghub.miniSpring.aop.MethodInvocation;

public class MyInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Exception {
		System.out.println("method "+invocation.getMethod()+" is called on "+
				invocation.getThis()+" with args "+invocation.getArguments());
		Object ret=invocation.proceed();
		System.out.println("method "+invocation.getMethod()+" returns "+ret);
		
		return ret;
	}

}
