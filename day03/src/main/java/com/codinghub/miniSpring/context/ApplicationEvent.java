package com.codinghub.miniSpring.context;

import java.util.EventObject;

/**
 * @author 莱特0905
 * @Description: 监听应用的事件
 * @Date: 2024/09/12 11:42:11
 */
public class ApplicationEvent extends EventObject {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
