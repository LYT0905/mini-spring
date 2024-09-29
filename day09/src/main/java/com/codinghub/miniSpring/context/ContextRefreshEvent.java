package com.codinghub.miniSpring.context;

/**
 * @author 莱特0905
 * @Description: 刷新事件
 * @Date: 2024/09/18 21:21:04
 */
public class ContextRefreshEvent extends ApplicationEvent{
    private static final long serialVersionUID = 1L;

    public ContextRefreshEvent(Object arg0) {
        super(arg0);
    }

    @Override
    public String toString() {
        return this.msg.toString();
    }
}
