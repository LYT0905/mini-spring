package com.codinghub.miniSpring.beans;

/**
 * @author 莱特0905
 * @Description: 抽象属性访问器
 * @Date: 2024/09/29 17:10:56
 */
public abstract class AbstractPropertyAccessor extends PropertyEditorRegistrySupport {
    PropertyValues pvs;

    public AbstractPropertyAccessor(){
        super();
    }

    /**
     * 设置属性值
     * @param pvs 属性值
     */
    public void setPropertyValues(PropertyValues pvs){
        this.pvs = pvs;
        for (PropertyValue pv : this.pvs.getPropertyValues()) {
            setPropertyValue(pv);
        }
    }

    /**
     * 设置属性值
     * @param pv 属性值
     */
    public abstract void setPropertyValue(PropertyValue pv);

}
