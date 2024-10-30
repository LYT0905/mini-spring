package com.codinghub.miniSpring.batis;

/**
 * @author 莱特0905
 * @Description: Mapper.xml文件转换对应的实体类
 * @Date: 2024/10/16 17:54:17
 */
public class MapperNode {
    /**
     * 映射的空间
     */
    String namespace;

    /**
     * 执行的方法
     */
    String id;

    /**
     * 参数类型
     */
    String parameterType;

    /**
     * 返回类型
     */
    String resultType;

    /**
     * SQL语句
     */
    String sql;

    /**
     * 参数
     */
    String parameter;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
