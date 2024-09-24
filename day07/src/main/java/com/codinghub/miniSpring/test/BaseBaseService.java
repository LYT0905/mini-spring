package com.codinghub.miniSpring.test;

public class BaseBaseService {
	private AServiceImpl as;
	
	public AServiceImpl getAs() {
		return as;
	}
	public void setAs(AServiceImpl as) {
		this.as = as;
	}
	public BaseBaseService() {
	}
	public void sayHello() {
		System.out.println("Base Base Service says hello");

	}

	public void sayGoodBye(){
		System.out.println("Base Base Service says goodBye!");
	}
}
