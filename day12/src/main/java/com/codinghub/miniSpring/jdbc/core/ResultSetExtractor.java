package com.codinghub.miniSpring.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 莱特0905
 * @Description: 把JDBC返回的ResultSet数据集映射为一个集合对象
 * @Date: 2024/10/15 19:39:18
 */
public interface ResultSetExtractor<T> {
    /**
     * 扩展数据
     * @param rs 结果集
     * @return 扩展后的数据
     * @throws SQLException SQL异常
     */
    T extractData(ResultSet rs) throws SQLException;
}
