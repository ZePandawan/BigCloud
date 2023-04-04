package com.polytechancy.BigCloud;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class BigCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigCloudApplication.class, args);
		        // Connexion à la base de données
		        Connection conn = null;
		        try {
		            conn = DriverManager.getConnection(
		                "jdbc:mariadb://51.210.242.34:3306/big_cloud",
		                "edashura",
		                "toutnwar619!");
		            System.out.println("Connexion réussie à la base de données MariaDB !");
		            String sql = "SHOW COLUMNS FROM Users";
		            Statement statement = conn.createStatement();
		            ResultSet result = statement.executeQuery(sql);
		            
		            // Traitement des résultats
		            while (result.next()) {
		                String tableName = result.getString(1);
		                System.out.println("Nom de la table : " + tableName);
		            }
		        } catch (SQLException e) {
		            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
		        } finally {
		            try {
		                if (conn != null) {
		                    conn.close();
		                }
		            } catch (SQLException e) {
		                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
		            }
		        }
		    }
	}
