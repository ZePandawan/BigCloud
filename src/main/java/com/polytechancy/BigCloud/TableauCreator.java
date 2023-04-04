package com.polytechancy.BigCloud;

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
    
        String[] filepart = input.split("/");
        String fileName = filepart[filepart.length - 1];
        String[] lines = input.split("\\r?\\n");
        for (String line : lines) {
            String[] parts = line.split("\\s+");
            tableau.append("<tr>\n");
            tableau.append("<td>").append(fileName).append("</td>\n");
            tableau.append("<td>").append(parts[4]).append(" octets").append("</td>\n");
            tableau.append("<td>").append(parts[6]).append(" ").append(parts[5]).append("</td>\n");
            tableau.append("<td>").append(parts[7]).append("</td>\n");
            tableau.append("</tr>\n");
        }
        tableau.append("</tbody>\n");
        tableau.append("</table>");
        return tableau.toString();
    }
}
