package com.polytechancy.BigCloud;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class BigCloudApplication {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		SpringApplication.run(BigCloudApplication.class, args);
		// Connexion à la base de données
		Connection conn = null;

		GetDataFromXML test = new GetDataFromXML();
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		test.readXmlFile(resourceLoader);

		try {
			conn = DriverManager.getConnection(
					"jdbc:mariadb://"+ test.getHost_bdd() +":3306/big_cloud",
					test.getUser_bdd(),
					test.getPassword_bdd());
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
