package com.codinghub.miniSpring.beans.factory.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莱特0905
 * @Description: 依赖注入参数实体对象集合类
 * @Date: 2024/09/12 14:18:08
 */
public class ArgumentValues {
    private final List<ArgumentValue> argumentValueList = new ArrayList<>();

    public ArgumentValues() {}

    /**
     * 添加注入参数实体对象
     * @param argumentValue 注入参数实体对象
     */
    public void addArgumentValue(ArgumentValue argumentValue){
        this.argumentValueList.add(argumentValue);
    }

    /**
     * 根据索引下标获取注入参数实体对象
     * @param index 索引下标
     * @return 注入参数实体对象
     */
    public ArgumentValue getIndexedArgumentValue(int index){
        ArgumentValue argumentValue = this.argumentValueList.get(index);
        return argumentValue;
    }

    /**
     * 获取参数数量
     * @return 参数数量
     */
    public int getArgumentCount(){
        return (this.argumentValueList.size());
    }

    /**
     * 判断参数列表是否为空
     * @return 为空返回true,否则返回false
     */
    public boolean isEmpty(){
        return (this.argumentValueList.isEmpty());
    }
}
