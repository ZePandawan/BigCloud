package com.polytechancy.BigCloud;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShareFile {


    public static String[] executeQuery(String id_file, String id_user) {
        String[] values = new String[2];
        String sqlSelect = "SELECT name FROM Users WHERE id_users="+id_user+";";
        String sqlUpdate = "UPDATE Files SET shares="+id_user+" WHERE id_file="+id_file;
        try {
            DataBaseAccess db = new DataBaseAccess();
            ResultSet resultSelect = db.executeQuery(sqlSelect);
            int rowsInserted = db.executeUpdate(sqlUpdate);
            System.out.println(rowsInserted + " ligne modifié Files.");
            if (!resultSelect.next()) {
                System.out.println("Pas de résultat");
            } else {
                String name = resultSelect.getString("name");
                values[0] = name;
            }
        } catch (SQLException | IOException | ParserConfigurationException | SAXException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
        return values;
    }
}
