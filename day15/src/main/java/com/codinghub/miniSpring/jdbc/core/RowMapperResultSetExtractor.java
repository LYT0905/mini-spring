package com.codinghub.miniSpring.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 莱特0905
 * @Description: 行映射器结果集提取器
 * @Date: 2024/10/15 19:41:24
 */
public class RowMapperResultSetExtractor<T> implements ResultSetExtractor<List<T>>{
    private final RowMapper<T> rowMapper;

    public RowMapperResultSetExtractor(RowMapper<T> rowMapper){
        this.rowMapper = rowMapper;
    }

    /**
     * 扩展数据
     * @param rs 结果集
     * @return 扩展后的数据
     * @throws SQLException SQL异常
     */
    @Override
    public List<T> extractData(ResultSet rs) throws SQLException {
        List<T> result = new ArrayList<>();
        int rowNum = 0;
        while (rs.next()){
            result.add(this.rowMapper.mapRow(rs, rowNum++));
        }
        return result;
    }
}
