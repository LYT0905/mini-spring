package com.codinghub.miniSpring.jdbc.core;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author 莱特0905
 * @Description: 语句回调(SQL语句)
 * @Date: 2024/10/08 20:20:39
 */
public interface StatementCallBack {
    /**
     * 执行语句
     * @param stmt 语句执行对象
     * @return 封装好的对象
     * @throws SQLException SQL异常
     */
    Object doInStatement(Statement stmt) throws SQLException;
}
