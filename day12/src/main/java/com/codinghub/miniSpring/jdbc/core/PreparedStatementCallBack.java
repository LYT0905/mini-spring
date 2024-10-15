package com.codinghub.miniSpring.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author 莱特0905
 * @Description: 预处理语句回调
 * @Date: 2024/10/08 20:23:01
 */
public interface PreparedStatementCallBack {
    /**
     * 执行预处理语句回调
     * @param stmt 预处理语句
     * @return 处理好的对象
     * @throws SQLException SQL异常
     */
    Object doInPreparedStatement(PreparedStatement stmt) throws SQLException;
}
