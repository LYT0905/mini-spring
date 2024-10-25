package com.codinghub.miniSpring.batis;

import com.codinghub.miniSpring.jdbc.core.JdbcTemplate;
import com.codinghub.miniSpring.jdbc.core.PreparedStatementCallBack;

/**
 * @author 莱特0905
 * @Description:
 * @Date: 2024/10/16 18:20:08
 */
public class DefaultSqlSession implements SqlSession{


    private JdbcTemplate jdbcTemplate;
    private SqlSessionFactory sqlSessionFactory;


    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Object selectOne(String sqlId, Object[] args, PreparedStatementCallBack pstmtcallback) {
        System.out.println(sqlId);
        String sql = this.sqlSessionFactory.getMapperNode(sqlId).getSql();
        System.out.println(sql);
        return jdbcTemplate.query(sql, args, pstmtcallback);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
