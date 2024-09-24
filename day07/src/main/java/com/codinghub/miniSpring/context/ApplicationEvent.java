package com.codinghub.miniSpring.context;

import java.util.EventObject;

/**
 * @author 莱特0905
 * @Description: 监听应用的事件
 * @Date: 2024/09/12 11:42:11
 */
public class ApplicationEvent extends EventObject {
    private static final long serialVersionUID = 1L;
    protected String msg = null;

    public ApplicationEvent(Object arg0) {
        super(arg0);
        this.msg = arg0.toString();
    }
}
