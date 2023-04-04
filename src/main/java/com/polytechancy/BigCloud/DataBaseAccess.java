package com.polytechancy.BigCloud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseAccess {
    private static final String URL = "jdbc:mariadb://bot.nightjs.ovh:3306/big_cloud";
    private static final String USER = "edashura";
    private static final String PASSWORD = "toutnwar619!";
    private Connection conn;
    
    public DataBaseAccess() throws SQLException {
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public ResultSet executeQuery(String sql) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(sql);
        return statement.executeQuery();
    }
    
    public int executeUpdate(String sql) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(sql);
        return statement.executeUpdate();
    }
    
    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
