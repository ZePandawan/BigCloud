package com.polytechancy.BigCloud;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseAccess {
    private Connection conn;
    
    public DataBaseAccess() throws SQLException, IOException, ParserConfigurationException, SAXException {
        GetDataFromXML datas_xml = new GetDataFromXML();
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        datas_xml.readXmlFile(resourceLoader);

        conn = DriverManager.getConnection("jdbc:mariadb://"+ datas_xml.getHost_bdd()+":3306/big_cloud", datas_xml.getUser_bdd(), datas_xml.getPassword_bdd());
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
