package com.codinghub.miniSpring.batis;

/**
 * @author 莱特0905
 * @Description: SQL会话工厂
 * @Date: 2024/10/16 17:56:37
 */
public interface SqlSessionFactory {
    /**
     * 打开SQL会话
     * @return SQL会话
     */
    SqlSession openSession();

    /**
     * 获取Mapper写的节点内容
     * @param name Mapper文件名字
     * @return Mapper写的节点内容
     */
    MapperNode getMapperNode(String name);
}
