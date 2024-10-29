package com.codinghub.miniSpring.test.controller;

import com.codinghub.miniSpring.beans.factory.annotation.Autowired;
import com.codinghub.miniSpring.test.User;
import com.codinghub.miniSpring.test.service.IAction;
import com.codinghub.miniSpring.test.service.UserService;
import com.codinghub.miniSpring.web.bind.annotation.RequestMapping;
import com.codinghub.miniSpring.web.bind.annotation.ResponseBody;
import com.codinghub.miniSpring.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 莱特0905
 * @Description: MVC测试Bean
 * @Date: 2024/09/23 17:55:15
 */
public class HelloWorldBean {

    @Autowired
    UserService userService;

    @Autowired
    IAction action;


    @RequestMapping("/hello")
    //@ResponseBody
    public String doGet() {
        return "hello world!";
    }

    @RequestMapping("/post")
    public String doPost() {
        return "hello post world!";
    }


    @RequestMapping("/test2")
    public void doTest2(HttpServletRequest request, HttpServletResponse response) {
        String str = "test 2, hello world!";
        try {
            response.getWriter().write(str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @RequestMapping("/test5")
    public ModelAndView doTest5(User user) {
        ModelAndView mav = new ModelAndView("test","msg",user.getName());
        return mav;
    }
    @RequestMapping("/test6")
    public String doTest6(User user) {
        return "error";
    }

    @RequestMapping("/test7")
    @ResponseBody
    public User doTest7(User user) {
        System.out.println(user.getBirthday());
        user.setName(user.getName() + "---");
        //user.setBirthday(new Date());
        return user;
    }

    @RequestMapping("/test8")
    @ResponseBody
    public User doTest8(HttpServletRequest request, HttpServletResponse response) {
        int userid = Integer.parseInt(request.getParameter("id"));
        User user = userService.getUserInfo(userid);
        return user;
    }

    @RequestMapping("/test9")
    @ResponseBody
    public List<User> doTest9(HttpServletRequest request, HttpServletResponse response) {
        int userid = Integer.parseInt(request.getParameter("id"));
        List<User> user = userService.getUsers(userid);
        return user;
    }


    @RequestMapping("/testaop")
    public void doTestAop(HttpServletRequest request, HttpServletResponse response) {

        //DynamicProxy proxy = new DynamicProxy(action);
        //IAction p = (IAction)proxy.getProxy();
        System.out.println("action -------------- " + action + "----------------");

        action.doAction();

        String str = "test aop, hello world!";
        try {
            response.getWriter().write(str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
