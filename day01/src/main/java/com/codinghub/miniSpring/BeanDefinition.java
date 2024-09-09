package com.codinghub.miniSpring;

/**
 * @author 莱特0905
 * @Description: 定义Bean
 * @Date: 2024/09/09 15:16:17
 */
public class BeanDefinition {
    /**
     * Bean 的 ID
     */
    private String id;
    /**
     * 类名
     */
    private String className;

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public BeanDefinition() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
