package com.codinghub.miniSpring.jdbc.core;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

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
            ArgumentPreparedStatementSetter argumentPreparedStatementSetter = new ArgumentPreparedStatementSetter(args);
            argumentPreparedStatementSetter.setValues(pstmt);

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

    public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper){
        RowMapperResultSetExtractor<T> resultSetExtractor = new RowMapperResultSetExtractor<>(rowMapper);
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement(sql);
            ArgumentPreparedStatementSetter argumentPreparedStatementSetter = new ArgumentPreparedStatementSetter(args);
            argumentPreparedStatementSetter.setValues(pstmt);
            rs = pstmt.executeQuery();
            return resultSetExtractor.extractData(rs);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                con.close();
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
