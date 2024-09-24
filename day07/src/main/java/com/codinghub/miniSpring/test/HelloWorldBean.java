package com.codinghub.miniSpring.test;

import com.codinghub.miniSpring.web.RequestMapping;

/**
 * @author 莱特0905
 * @Description: MVC测试Bean
 * @Date: 2024/09/23 17:55:15
 */
public class HelloWorldBean {
    @RequestMapping("/hello")
    public String doGet() {
        return "hello world!";
    }

    @RequestMapping("/post")
    public String doPost() {
        return "hello post world!";
    }
}
