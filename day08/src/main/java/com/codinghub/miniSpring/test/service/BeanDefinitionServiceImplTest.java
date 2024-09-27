package com.codinghub.miniSpring.test.service;

/**
 * @author 莱特0905
 * @Description: 定义Bean服务测试实现类
 * @Date: 2024/09/09 15:53:08
 */
public class BeanDefinitionServiceImplTest implements BeanDefinitionServiceTest {

    private String name;
    private int level;
    private String property1;
    private String property2;

    public BeanDefinitionServiceImplTest() {
    }
    public BeanDefinitionServiceImplTest(String name, int level) {
        this.name = name;
        this.level = level;
        System.out.println(this.name + "," + this.level);
    }

    @Override
    public void print() {
        System.out.println("BeanDefinitionTest Successfully");
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
}



