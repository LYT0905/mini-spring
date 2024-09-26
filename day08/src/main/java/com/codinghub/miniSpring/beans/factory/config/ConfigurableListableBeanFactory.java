package com.codinghub.miniSpring.beans.factory.config;

import com.codinghub.miniSpring.beans.factory.ListableBeanFactory;

/**
 * @author 莱特0905
 * @Description: 可配置的可列表Bean工厂
 * @Date: 2024/09/18 20:29:16
 */
public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
}
