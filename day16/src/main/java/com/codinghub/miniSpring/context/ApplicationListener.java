package com.codinghub.miniSpring.context;

import java.util.EventListener;

/**
 * @author 莱特0905
 * @Description: 应用监听对象
 * @Date: 2024/09/18 21:19:35
 */
public class ApplicationListener implements EventListener {
    void onApplicationEvent(ApplicationEvent event){
        System.out.println(event.toString());
    }
}
