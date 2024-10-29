package com.codinghub.miniSpring.beans;

/**
 * @author 莱特0905
 * @Description: 自定义Bean异常类
 * @Date: 2024/09/09 16:10:02
 */
public class BeansException extends Exception{
    public BeansException(String msg){
        super(msg);
    }
}
