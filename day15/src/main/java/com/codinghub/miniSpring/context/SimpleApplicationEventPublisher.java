package com.codinghub.miniSpring.context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莱特0905
 * @Description: 简单应用事件发布者
 * @Date: 2024/09/18 21:22:25
 */
public class SimpleApplicationEventPublisher implements ApplicationEventPublisher{
    List<ApplicationListener> listeners = new ArrayList<>();

    /**
     * 发布事件
     * @param event 事件
     */
    @Override
    public void publishEvent(ApplicationEvent event) {
        for (ApplicationListener listener : listeners) {
            listener.onApplicationEvent(event);
        }
    }

    /**
     * 添加应用监听
     * @param listener 监听
     */
    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.listeners.add(listener);
    }
}
