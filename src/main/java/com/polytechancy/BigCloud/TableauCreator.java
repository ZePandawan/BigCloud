package com.polytechancy.BigCloud;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableauCreator {
	public static String generertableau(String input) {
        StringBuilder tableau = new StringBuilder();
        tableau.append("<table>\n");
        
        // Header row
        tableau.append("<thead>\n");
        tableau.append("<tr>\n");
        tableau.append("<th>Nom du fichier</th>\n");
        tableau.append("<th>Taille du fichier</th>\n");
        tableau.append("<th>Date de création</th>\n");
        tableau.append("<th>Heure de dernière modification</th>\n");
        tableau.append("</tr>\n");
        tableau.append("</thead>\n");
        
        // Data rows
        tableau.append("<tbody>\n");
        try {
            DataBaseAccess db = new DataBaseAccess();

            // Exécution d'une requête SELECT
            String sqlSelect = "SELECT name,size,creation_date,time FROM Files WHERE owner='"+input+"';";
            ResultSet resultSelect = db.executeQuery(sqlSelect);
            while (resultSelect.next()) {
                String name = resultSelect.getString("name");
                Double size = resultSelect.getDouble("size");
                String creation_date = resultSelect.getString("creation_date");
                String time = resultSelect.getString("time");
                tableau.append("<tr>\n");
                tableau.append("<td>").append(name).append("</td>\n");
                tableau.append("<td>").append(size).append(" octets").append("</td>\n");
                tableau.append("<td>").append(creation_date).append("</td>\n");
                tableau.append("<td>").append(time).append("</td>\n");
                tableau.append("</tr>\n");
            }
            db.close();
        } catch (SQLException | IOException | ParserConfigurationException | SAXException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            return "Login";
        }
        tableau.append("</tbody>\n");
        tableau.append("</table>");
        return tableau.toString();
    }
}
