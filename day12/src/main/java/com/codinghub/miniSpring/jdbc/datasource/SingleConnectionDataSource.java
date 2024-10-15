package com.codinghub.miniSpring.jdbc.datasource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author 莱特0905
 * @Description: 单连接数据源
 * @Date: 2024/10/08 20:32:12
 */
public class SingleConnectionDataSource implements DataSource {
    /**
     * 数据库驱动类名
     */
    private String driverClassName;

    /**
     * 数据库连接地址
     */
    private String url;

    /**
     * 数据库用户名
     */
    private String username;

    /**
     * 数据库密码
     */
    private String password;

    /**
     * 数据库连接配置
     */
    private Properties connectionProperties;

    /**
     * 数据库连接
     */
    private Connection connection;

    public SingleConnectionDataSource() {
    }

    /**
     * 获取数据库连接对象
     * @return 数据库连接对象
     * @throws SQLException SQL异常
     */
    @Override
    public Connection getConnection() throws SQLException {
        return getConnectionFromDriver(getUsername(), getPassword());
    }

    /**
     * 获取数据库连接对象
     * @param username the database user on whose behalf the connection is
     *  being made
     * @param password the user's password
     * @return 数据库连接对象
     * @throws SQLException SQL异常
     */
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnectionFromDriver(username, password);
    }

    /**
     * 从驱动程序获取连接
     * @param username 用户名
     * @param password 密码
     * @return 连接对象
     * @throws SQLException SQL异常
     */
    protected Connection getConnectionFromDriver(String username, String password) throws SQLException{
        Properties mergedProps = new Properties();
        Properties connProps = getConnectionProperties();
        if (connProps != null){
            mergedProps.putAll(connProps);
        }
        if (username != null){
            mergedProps.setProperty("user", username);
        }
        if (password != null){
            mergedProps.setProperty("password", password);
        }
        this.connection = getConnectionFromDriverManager(getUrl(), mergedProps);
        return this.connection;
    }

    /**
     * 从驱动程序管理器获取连接
     * @param url 数据库地址
     * @param props 数据库连接配置
     * @return 连接对象
     * @throws SQLException SQL异常
     */
    protected Connection getConnectionFromDriverManager(String url, Properties props) throws SQLException{
        return DriverManager.getConnection(url, props);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    /**
     * 设置驱动类名称
     * @param driverClassName 驱动类名称
     */
    public void setDriverClassName(String driverClassName){
        this.driverClassName = driverClassName;
        try {
            Class.forName(driverClassName);
        }catch (ClassNotFoundException ex){
            throw new IllegalStateException("Could not load JDBC driver class [" + driverClassName + "]", ex);
        }
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(Properties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }
}
