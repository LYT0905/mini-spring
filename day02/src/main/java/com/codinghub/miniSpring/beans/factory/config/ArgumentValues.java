package com.codinghub.miniSpring.beans.factory.config;

import java.util.*;

/**
 * @author 莱特0905
 * @Description: 依赖注入参数实体对象集合类
 * @Date: 2024/09/12 14:18:08
 */
public class ArgumentValues {
    private final Map<Integer, ArgumentValue> indexedArgumentValues = new HashMap<>();
    private final List<ArgumentValue> genericArgumentValues = new LinkedList<>();


    public ArgumentValues() {
    }

    /**
     * 添加注入参数实体对象
     * @param key 注入参数实体对象键
     * @param argumentValue 注入参数实体对象
     */
    private void addArgumentValue(Integer key, ArgumentValue argumentValue){
        this.indexedArgumentValues.put(key, argumentValue);
    }

    /**
     * 根据索引下标判断是否存在注入参数实体对象
     * @param index 索引下标
     * @return 存在返回true，不存在返回false
     */
    public boolean hasIndexedArgumentValue(int index){
        return this.indexedArgumentValues.containsKey(index);
    }

    /**
     * 根据索引下标获取注入参数实体对象
     * @param index 索引下标
     * @return 注入参数实体对象
     */
    public ArgumentValue getIndexedArgumentValue(int index){
        return this.indexedArgumentValues.get(index);
    }

    /**
     * 添加通用注入参数实体对象
     * @param newValue  注入参数实体对象
     */
    private void addGenericArgumentValue(ArgumentValue newValue){
        if (newValue.getName() != null){
            for (Iterator<ArgumentValue> it = this.genericArgumentValues.iterator(); it.hasNext();){
                ArgumentValue currentValue = it.next();
                if (newValue.getName().equals(currentValue.getName())){
                    it.remove();
                }
            }
        }
        this.genericArgumentValues.add(newValue);
    }

    /**
     * 根据需要获取的名字获取注入参数实体对象
     * @param requiredName 需要获取的名字
     * @return 注入参数实体对象
     */
    public ArgumentValue getGenericArgumentValue(String requiredName){
        for (ArgumentValue valueHolder : this.genericArgumentValues){
            if (valueHolder.getName() != null && (requiredName == null || !valueHolder.getName().equals(requiredName))){
                continue;
            }
            return valueHolder;
        }
        return null;
    }

    /**
     * 获取参数数量
     * @return 参数数量
     */
    public int getArgumentCount(){
        return this.genericArgumentValues.size();
    }

    /**
     * 判断参数列表是否为空
     * @return 为空返回true,否则返回false
     */
    public boolean isEmpty(){
        return this.genericArgumentValues.isEmpty();
    }
}
