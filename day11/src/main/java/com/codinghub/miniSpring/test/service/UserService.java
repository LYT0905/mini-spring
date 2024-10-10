package com.codinghub.miniSpring.test.service;

import com.codinghub.miniSpring.beans.factory.annotation.Autowired;
import com.codinghub.miniSpring.jdbc.core.JdbcTemplate;
import com.codinghub.miniSpring.jdbc.core.PreparedStatementCallBack;
import com.codinghub.miniSpring.test.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 莱特0905
 * @Description:
 * @Date: 2024/10/08 20:47:33
 */
public class UserService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public User getUserInfo(int userid) {
        final String sql = "select id, name, age, birthday from user where id = ?";
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
        User query = (User) jdbcTemplate.query(sql, new Object[]{new Integer(userid)}, preparedStatementCallBack);
        return query;
    }
}
