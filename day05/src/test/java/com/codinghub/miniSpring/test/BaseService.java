package com.codinghub.miniSpring.test;

import com.codinghub.miniSpring.beans.factory.annotation.Autowired;

public class BaseService {
    @Autowired
    private BaseBaseService bbs;
    public BaseBaseService getBbs() {
        return bbs;
    }
    public void setBbs(BaseBaseService bbs) {
        this.bbs = bbs;
    }
    public BaseService() {}
    public void sayHello() {
        System.out.println("Base Service says Hello");
        bbs.sayHello();
    }
    public void init() {
        System.out.print("Base Service init method.");
    }
}