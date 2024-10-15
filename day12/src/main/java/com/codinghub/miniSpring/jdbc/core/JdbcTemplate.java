package com.codinghub.miniSpring.jdbc.core;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;

/**
 * @author 莱特0905
 * @Description: JDBC模板
 * @Date: 2024/10/08 20:19:05
 */
public class JdbcTemplate {
    /**
     * 数据源
     */
    private DataSource dataSource;

    public JdbcTemplate() {
    }

    /**
     * 执行查询语句
     * @param stmtcallback 语句回调对象
     * @return 执行语句对象
     */
    public Object query(StatementCallBack stmtcallback){
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();

            return stmtcallback.doInStatement(stmt);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                conn.close();
                stmt.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    public Object query(String sql, Object[] args, PreparedStatementCallBack pstmtcallback){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            if (args != null){
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    if (arg instanceof String){
                        pstmt.setString(i + 1, (String) arg);
                    } else if (arg instanceof Integer) {
                        pstmt.setInt(i + 1, (int) arg);
                    }else if (arg instanceof Date){
                        pstmt.setDate(i + 1, new java.sql.Date(((Date)arg).getTime()));
                    }
                }
            }

            return pstmtcallback.doInPreparedStatement(pstmt);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                conn.close();
                pstmt.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
