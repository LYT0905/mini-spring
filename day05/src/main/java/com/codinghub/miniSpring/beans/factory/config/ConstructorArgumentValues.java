package com.codinghub.miniSpring.beans.factory.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莱特0905
 * @Description: 依赖注入参数实体对象集合类
 * @Date: 2024/09/12 14:18:08
 */
public class ConstructorArgumentValues {
    private final List<ConstructorArgumentValue> constructorArgumentValueList = new ArrayList<>();

    public ConstructorArgumentValues() {}

    /**
     * 添加注入参数实体对象
     * @param constructorArgumentValue 注入参数实体对象
     */
    public void addArgumentValue(ConstructorArgumentValue constructorArgumentValue){
        this.constructorArgumentValueList.add(constructorArgumentValue);
    }

    /**
     * 根据索引下标获取注入参数实体对象
     * @param index 索引下标
     * @return 注入参数实体对象
     */
    public ConstructorArgumentValue getIndexedArgumentValue(int index){
        ConstructorArgumentValue constructorArgumentValue = this.constructorArgumentValueList.get(index);
        return constructorArgumentValue;
    }

    /**
     * 获取参数数量
     * @return 参数数量
     */
    public int getArgumentCount(){
        return (this.constructorArgumentValueList.size());
    }

    /**
     * 判断参数列表是否为空
     * @return 为空返回true,否则返回false
     */
    public boolean isEmpty(){
        return (this.constructorArgumentValueList.isEmpty());
    }
}
