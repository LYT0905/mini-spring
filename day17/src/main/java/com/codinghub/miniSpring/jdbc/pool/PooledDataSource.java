package com.codinghub.miniSpring.jdbc.pool;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author 莱特0905
 * @Description: 连接池数据源
 * @Date: 2024/10/15 19:57:21
 */
public class PooledDataSource implements DataSource {
    /**
     * 连接池对象
     */
    private List<PooledConnection> connections = null;

    /**
     * 驱动类目
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
     * 初始化的连接池大小(默认10)
     */
    private int initialSize = 10;

    /**
     * 连接配置
     */
    private Properties connectionProperties;

    public PooledDataSource() {
    }

    private void initPool(){
        this.connections = new ArrayList<>(initialSize);
        try {
            for (int i = 0; i < initialSize; i++) {
                Connection connection = DriverManager.getConnection(url, username, password);
                PooledConnection pooledConnection = new PooledConnection(connection, false);
                this.connections.add(pooledConnection);
                System.out.println("********add connection pool*********");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnectionFomDriver(getUsername(), getPassword());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnectionFomDriver(username, password);
    }

    /**
     * 从驱动获取连接对象
     * @param username 用户名
     * @param password 密码
     * @return 连接对象
     * @throws SQLException SQL异常
     */
    protected Connection getConnectionFomDriver(String username, String password) throws SQLException{
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
        if (this.connections == null){
            initPool();
        }

        PooledConnection pooledConnection = getAvailableConnection();
        while (pooledConnection == null){
            pooledConnection = getAvailableConnection();
            if (pooledConnection == null){
                try {
                    TimeUnit.MILLISECONDS.sleep(30);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        return pooledConnection;
    }

    /**
     * 获取空闲的连接
     * @return 空闲的连接
     * @throws SQLException SQL异常
     */
    private PooledConnection getAvailableConnection() throws SQLException{
        for (PooledConnection pooledConnection : this.connections) {
            if (!pooledConnection.isActive()){
                pooledConnection.setActive(true);
                return pooledConnection;
            }
        }
        return null;
    }

    public List<PooledConnection> getConnections() {
        return connections;
    }

    public void setConnections(List<PooledConnection> connections) {
        this.connections = connections;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
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

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(Properties connectionProperties) {
        this.connectionProperties = connectionProperties;
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
}
