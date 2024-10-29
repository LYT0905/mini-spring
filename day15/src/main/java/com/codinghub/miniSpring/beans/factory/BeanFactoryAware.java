package com.codinghub.miniSpring.beans.factory;

/**
 * @author 莱特0905
 * @Description: Bean工厂感知接口
 * @Date: 2024/10/29 19:36:13
 */
public interface BeanFactoryAware {
    /**
     * 设置Bean工厂
     * @param beanFactory Bean工厂
     */
    void setBeanFactory(BeanFactory beanFactory);
}
