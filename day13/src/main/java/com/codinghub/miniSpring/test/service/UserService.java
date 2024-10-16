package com.codinghub.miniSpring.test.service;

import com.codinghub.miniSpring.batis.DefaultSqlSession;
import com.codinghub.miniSpring.batis.DefaultSqlSessionFactory;
import com.codinghub.miniSpring.batis.SqlSession;
import com.codinghub.miniSpring.beans.factory.annotation.Autowired;
import com.codinghub.miniSpring.jdbc.core.JdbcTemplate;
import com.codinghub.miniSpring.jdbc.core.PreparedStatementCallBack;
import com.codinghub.miniSpring.jdbc.core.RowMapper;
import com.codinghub.miniSpring.test.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 莱特0905
 * @Description:
 * @Date: 2024/10/08 20:47:33
 */
public class UserService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DefaultSqlSessionFactory sqlSessionFactory;

    public User getUserInfo(int userid) {
        String sqlId = "com.codinghub.miniSpring.test.User.getUserInfo";
        SqlSession sqlSession = sqlSessionFactory.openSession();

        PreparedStatementCallBack preparedStatementCallBack = new PreparedStatementCallBack() {
            @Override
            public Object doInPreparedStatement(PreparedStatement stmt) throws SQLException {
                ResultSet rs = stmt.executeQuery();
                User rtnUser = null;
                if (rs.next()) {
                    rtnUser = new User();
                    rtnUser.setId(userid);
                    rtnUser.setAge(rs.getInt("age"));
                    rtnUser.setName(rs.getString("name"));
                    rtnUser.setBirthday(new java.util.Date(rs.getDate("birthday").getTime()));
                } else {
                }
                return rtnUser;
            }
        };
        User query = (User) sqlSession.selectOne(sqlId, new Object[]{new Integer(userid)}, preparedStatementCallBack);
        return query;
    }

//    public User getUserInfo(int userid) {
//        final String sql = "select id, name, age, birthday from user where id = ?";
//        PreparedStatementCallBack preparedStatementCallBack = new PreparedStatementCallBack() {
//            @Override
//            public Object doInPreparedStatement(PreparedStatement stmt) throws SQLException {
//                ResultSet rs = stmt.executeQuery();
//                User rtnUser = null;
//                if (rs.next()) {
//                    rtnUser = new User();
//                    rtnUser.setId(userid);
//                    rtnUser.setAge(rs.getInt("age"));
//                    rtnUser.setName(rs.getString("name"));
//                    rtnUser.setBirthday(new java.util.Date(rs.getDate("birthday").getTime()));
//                } else {
//                }
//                return rtnUser;
//            }
//        };
//        User query = (User) jdbcTemplate.query(sql, new Object[]{new Integer(userid)}, preparedStatementCallBack);
//        return query;
//    }

    public List<User> getUsers(int userId){
        final String sql = "select id, name, age, birthday from user where id >= ?";
        RowMapper<User> rowMapper = new RowMapper<User>(){

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User rtnUser = new User();
                rtnUser.setId(rs.getInt("id"));
                rtnUser.setAge(rs.getInt("age"));
                rtnUser.setName(rs.getString("name"));
                rtnUser.setBirthday(new java.util.Date(rs.getDate("birthday").getTime()));
                return rtnUser;
            }
        };
        return jdbcTemplate.query(sql, new Object[]{new Integer(userId)}, rowMapper);
    }
}
