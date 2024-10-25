package com.codinghub.miniSpring.web.bind.support;

import com.codinghub.miniSpring.web.bind.WebDataBinder;

/**
 * @author 莱特0905
 * @Description: Web绑定初始化对象
 * @Date: 2024/09/29 17:37:28
 */
public interface WebBindingInitializer {
    /**
     * 初始化绑定对象
     * @param binder Web数据绑定对象
     */
    void initBinder(WebDataBinder binder);
}
