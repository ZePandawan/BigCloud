package com.polytechancy.BigCloud;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoveFile {

    public static void removeRow(String sqlDelete) {
        try {
            DataBaseAccess db = new DataBaseAccess();
            int rowsInserted = db.executeUpdate(sqlDelete);
            //System.out.println(rowsInserted + " ligne supprimé dans Files.");
            db.close();
        } catch (SQLException | IOException | ParserConfigurationException | SAXException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }
    public static String[] executeQuery(String sqlSelect) {
        String[] values = new String[2];
        try {
            DataBaseAccess db = new DataBaseAccess();
            ResultSet resultSelect = db.executeQuery(sqlSelect);
            if (!resultSelect.next()) {
                //System.out.println("Pas de résultat");
            } else {
                String name = resultSelect.getString("name");
                String file_location = resultSelect.getString("file_location");
                values[0] = name;
                values[1] = file_location;
            }
        } catch (SQLException | IOException | ParserConfigurationException | SAXException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
        return values;
    }
}
