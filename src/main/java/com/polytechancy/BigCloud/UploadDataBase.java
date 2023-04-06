package com.polytechancy.BigCloud;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UploadDataBase {
    private String username;
    private String filename;

    public UploadDataBase(String username, String filename) {
        this.username = username;
        this.filename = filename;
    }

    public void upload() {
        String filenameformated = filename.replaceAll(" ", "\\\\ ");
        String localisation = "ls -al /var/BigCloud/"+username+"/"+filenameformated;
        System.out.println(localisation);
        try {
            GetDataFromXML XML_datas = new GetDataFromXML();
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            XML_datas.readXmlFile(resourceLoader);
            SshTunnel sshConnector = new SshTunnel(XML_datas.getHost_ssh(), XML_datas.getUser_ssh(), XML_datas.getPassword_ssh());
            String output = sshConnector.executeCommand(localisation);
            System.out.println(output);
            sshConnector.disconnect();
            String[] parts = output.split("\\s+");

            for (String part : parts) {
                System.out.println(part);
            }
            System.out.println("Nom du fichier "+filename);
            System.out.println("Taille "+parts[4]);
            System.out.println("Date "+parts[6]+parts[5]);
            System.out.println("Heure "+parts[7]);
            System.out.println("Utilisateur "+username);
            System.out.println("Localisation /var/BigCloud/"+username+"/"+filenameformated);
            try {
                DataBaseAccess db = new DataBaseAccess();

                String sqlInsert = "INSERT INTO Files VALUES (null, '"+filename+"', '"+parts[4]+"', '"+parts[6]+" "+parts[5]+"', '"+localisation+"','"+username+"',null, '"+parts[7]+"')";
                int rowsInserted = db.executeUpdate(sqlInsert);
                System.out.println(rowsInserted + " lignes ont été insérées.");

                db.close();
            } catch (SQLException | IOException | ParserConfigurationException | SAXException e) {
                System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            }
        } catch (JSchException | IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
}

