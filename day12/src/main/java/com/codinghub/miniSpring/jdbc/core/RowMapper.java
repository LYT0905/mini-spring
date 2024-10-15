package com.codinghub.miniSpring.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 莱特0905
 * @Description: 处理每一行的数据，可以返回多对象
 * @Date: 2024/10/15 19:36:11
 */
public interface RowMapper<T> {
    /**
     * 处理行数据
     * @param rs 结果集
     * @param rowNum 行号
     * @throws SQLException SQL异常
     */
    T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
