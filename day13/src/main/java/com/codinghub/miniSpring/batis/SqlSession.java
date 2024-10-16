package com.codinghub.miniSpring.batis;

import com.codinghub.miniSpring.jdbc.core.JdbcTemplate;
import com.codinghub.miniSpring.jdbc.core.PreparedStatementCallBack;

/**
 * @author 莱特0905
 * @Description: SQL会话
 * @Date: 2024/10/16 17:57:09
 */
public interface SqlSession {
    /**
     * 设置JDBC模板
     * @param jdbcTemplate JDBC模板
     */
    void setJdbcTemplate(JdbcTemplate jdbcTemplate);

    /**
     * 设置SQL会话工厂
     * @param sqlSessionFactory SQL会话工厂
     */
    void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);

    /**
     * 获取单个
     * @param sqlId SQL方法
     * @param args 参数
     * @param pstmtcallback 预处理语句回调
     * @return 返回查询信息
     */
    Object selectOne(String sqlId, Object[] args, PreparedStatementCallBack pstmtcallback);
}
