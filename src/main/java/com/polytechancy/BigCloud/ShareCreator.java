package com.polytechancy.BigCloud;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShareCreator {
    public static String SelectCreator(String input) {
        StringBuilder tableau = new StringBuilder();
        try {
            DataBaseAccess db = new DataBaseAccess();
            tableau.append("<form method='post'>");
            tableau.append("<select name='file'>");
            // Exécution d'une requête SELECT
            String sqlSelect = "SELECT id_file,name FROM Files WHERE owner='" + input + "';";
            ResultSet resultSelect = db.executeQuery(sqlSelect);
            while (resultSelect.next()) {
                String value = resultSelect.getString("id_file");
                String name = resultSelect.getString("name");
                String option = "<option value='" + value + "'>" + name + "</option>";
                tableau.append(option);
            }
            tableau.append("</select>");
            tableau.append("<select name='user'>");
            String sqlSelect2 = "SELECT id_users,name FROM Users;";
            ResultSet resultSelect2 = db.executeQuery(sqlSelect2);
            while (resultSelect2.next()) {
                String name = resultSelect2.getString("name");
                String value = resultSelect2.getString("id_users");
                String option = "<option value='" + value + "'>" + name + "</option>";
                tableau.append(option);
            }
            tableau.append("</select>");
            tableau.append("<input type='submit' value='Partager'>");
            tableau.append("</form>");
            db.close();
        } catch (SQLException | IOException | ParserConfigurationException | SAXException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
        return tableau.toString();
    }
}
