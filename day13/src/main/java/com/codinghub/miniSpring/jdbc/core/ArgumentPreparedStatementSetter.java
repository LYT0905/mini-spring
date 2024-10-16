package com.codinghub.miniSpring.jdbc.core;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author 莱特0905
 * @Description: 设置参数的实体类(将参数包装)
 * @Date: 2024/10/15 19:25:16
 */
public class ArgumentPreparedStatementSetter {
    /**
     * 参数
     */
    private final Object[] args;

    public ArgumentPreparedStatementSetter(Object[] args){
        this.args = args;
    }

    /**
     * 对外暴露设置参数的方法
     * @param pstmt 定义预准备的对象
     * @throws SQLException SQL异常
     */
    public void setValues(PreparedStatement pstmt) throws SQLException{
        if (this.args != null){
            for (int i = 0; i < this.args.length; i++) {
                Object arg = args[i];
                doSetValue(pstmt, i + 1, arg);
            }
        }
    }

    /**
     * 真正进行设置值的方法
     * @param pstmt 定义预准备的对象
     * @param parameterPosition 参数位置
     * @param arg 参数
     */
    protected void doSetValue(PreparedStatement pstmt, int parameterPosition, Object arg) throws SQLException{
        if(arg instanceof String){
            pstmt.setString(parameterPosition, (String) arg);
        } else if (arg instanceof Integer) {
            pstmt.setInt(parameterPosition, (int) arg);
        } else if (arg instanceof java.util.Date) {
            pstmt.setDate(parameterPosition, new Date(((java.util.Date) arg).getTime()));
        }
    }

}
