package com.codinghub.miniSpring.context;

/**
 * @author 莱特0905
 * @Description: 发布事件
 * @Date: 2024/09/12 11:42:33
 */
public interface ApplicationEventPublisher {
    /**
     * 发布事件
     * @param event 事件
     */
    void publishEvent(ApplicationEvent event);

    /**
     * 添加应用监听
     * @param listener 监听
     */
    void addApplicationListener(ApplicationListener listener);
}
