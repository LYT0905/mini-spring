package com.codinghub.miniSpring.test;

public class AServiceImpl implements AService {
    private String name;
    private int level;
    private String property1;
    private String property2;
    private BaseService ref1;

    public AServiceImpl(String name, int level, String property1, String property2, BaseService ref1) {
        this.name = name;
        this.level = level;
        this.property1 = property1;
        this.property2 = property2;
        this.ref1 = ref1;
    }

    public AServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public AServiceImpl( int level) {
        this.level = level;
    }

    public AServiceImpl(String name) {
        this.name = name;
    }

    public AServiceImpl() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public BaseService getRef1() {
        return ref1;
    }

    public void setRef1(BaseService ref1) {
        this.ref1 = ref1;
    }

    @Override
    public void sayHello() {
        System.out.println("IoC Successfully");
    }
}