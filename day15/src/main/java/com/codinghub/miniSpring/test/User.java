package com.codinghub.miniSpring.test;

import java.util.Date;

public class User {
	int id = 1;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	String name = "lihua";
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	Date birthday = new Date();

	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	int age;

	public void setAge(int age){
		this.age = age;
	}

	public int getAge() {
		return age;
	}
}