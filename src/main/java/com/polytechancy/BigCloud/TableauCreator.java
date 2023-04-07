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
        tableau.append("<caption> Mes documents </caption>\n");
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
            String sqlSelect = "SELECT id_file,name,size,creation_date,time FROM Files WHERE owner='"+input+"';";
            ResultSet resultSelect = db.executeQuery(sqlSelect);
            while (resultSelect.next()) {
                int id_file = resultSelect.getInt("id_file");
                String name = resultSelect.getString("name");
                Double size = resultSelect.getDouble("size");
                String creation_date = resultSelect.getString("creation_date");
                String time = resultSelect.getString("time");
                String form = "<td><form method='post'><input type='hidden' name='remove' value='" + id_file + "'><input type='submit' value='delete'></form>";
                String formdownload = "<td><form method='post' action='/download'><input type='hidden' name='download' value='" + id_file + "'><input type='submit' value='download'></form>";
                tableau.append("<tr>\n");
                tableau.append("<td>").append(name).append("</td>\n");
                tableau.append("<td>").append(size).append(" octets").append("</td>\n");
                tableau.append("<td>").append(creation_date).append("</td>\n");
                tableau.append("<td>").append(time).append("</td>\n");
                tableau.append("<td>").append(form).append("</td>\n");
                tableau.append("<td>").append(formdownload).append("</td>\n");
                tableau.append("</tr>\n");
            }
            tableau.append("</tbody>\n");
            tableau.append("</table>");
            tableau.append("<table>\n");
            tableau.append("<caption> Documents partagés </caption>\n");
            // Header row
            tableau.append("<thead>\n");
            tableau.append("<tr>\n");
            tableau.append("<th>Nom du fichier</th>\n");
            tableau.append("<th>Taille du fichier</th>\n");
            tableau.append("<th>Date de création</th>\n");
            tableau.append("<th>Heure de dernière modification</th>\n");
            tableau.append("<th>Partagé par :</th>\n");
            tableau.append("</tr>\n");
            tableau.append("</thead>\n");

            // Data rows
            tableau.append("<tbody>\n");
            String id_user = null;
            String sqlSelectId = "SELECT id_users FROM Users WHERE name='"+input+"';";
            System.out.println(sqlSelectId);
            ResultSet resultSelectId = db.executeQuery(sqlSelectId);
            if (!resultSelectId.next()) {
                System.out.println("Pas de résultat");
            } else {
                id_user = resultSelectId.getString("id_users");
            }
            String sqlSelect2 = "SELECT id_file,name,size,creation_date,time,owner FROM Files WHERE shares='"+id_user+"';";
            ResultSet resultSelect2 = db.executeQuery(sqlSelect2);
            while (resultSelect2.next()) {
                String name = resultSelect2.getString("name");
                Double size = resultSelect2.getDouble("size");
                String creation_date = resultSelect2.getString("creation_date");
                String time = resultSelect2.getString("time");
                String id_file = resultSelect2.getString("id_file");
                String owner = resultSelect2.getString("owner");
                tableau.append("<tr>\n");
                tableau.append("<td>").append(name).append("</td>\n");
                tableau.append("<td>").append(size).append(" octets").append("</td>\n");
                tableau.append("<td>").append(creation_date).append("</td>\n");
                tableau.append("<td>").append(time).append("</td>\n");
                tableau.append("<td>").append(owner).append("</td>\n");
                String formdownload = "<td><form method='post'><input type='hidden' name='download' value='" + id_file + "'><input type='submit' value='download'></form>";
                tableau.append("<td>").append(formdownload).append("</td>\n");
                tableau.append("</tr>\n");
            }
            tableau.append("</tbody>\n");
            tableau.append("</table>");
            db.close();
        } catch (SQLException | IOException | ParserConfigurationException | SAXException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            return "Login";
        }

        return tableau.toString();
    }
}
