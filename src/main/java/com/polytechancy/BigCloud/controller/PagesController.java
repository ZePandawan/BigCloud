package com.polytechancy.BigCloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.polytechancy.BigCloud.DataBaseAccess;
import com.polytechancy.BigCloud.MD5Hasher;
import com.polytechancy.BigCloud.SshTunnel;
import com.polytechancy.BigCloud.TableauCreator;

import jakarta.servlet.http.HttpSession;

@Controller
public class PagesController {
	
	
	
	@GetMapping("/accueil")
	public String afficherPageAccueil(HttpSession session, ModelMap model) {
		String nom = (String) session.getAttribute("Nom");
		System.out.println(nom);
		session.setAttribute("Nom",nom);
		if (nom == null) {
			return "redirect:/login";
		}
		try {
		    SshTunnel sshConnector = new SshTunnel("bot.nightjs.ovh", "root", "toutnwar619!");
		    String output = sshConnector.executeCommand("ls -al /var/BigCloud/"+nom+"/*");
		    System.out.println(output);
		    sshConnector.disconnect();
		    String tableauhtml = TableauCreator.generertableau(output);
		    System.out.println(tableauhtml);
		    model.put("tableau", tableauhtml);
		} catch (JSchException | IOException e) {
		    e.printStackTrace();
		    model.put("errorMsg", "Impossible de récurer vos documents");
     	   return "Accueil";
		}
		 
	    return "Accueil";
	}
	@GetMapping("/")
	public String home() {
		return "Rules";
	}
	@GetMapping("/login")
	public String login() {
		return "Login";
	}
	@PostMapping("/login")
	public String postlogin(ModelMap model, @RequestParam String email, @RequestParam String password, String name, HttpSession session) {
		try {
            DataBaseAccess db = new DataBaseAccess();
            
            // Exécution d'une requête SELECT
            String sqlSelect = "SELECT mail,password,name FROM Users WHERE mail='"+email+"';";
            System.out.println(sqlSelect);
            ResultSet resultSelect = db.executeQuery(sqlSelect);
            if (!resultSelect.next()) {
            	System.out.println("COMPTE INCORRECT (INCONNU)");
            	model.put("errorMsg", "Compte incorrect !");
            	return "Login";
            }
            else {
                String mail = resultSelect.getString("mail");
                String loginpassword = resultSelect.getString("password");
                name = resultSelect.getString("name");
               if (!email.equals(mail)) {
            	   model.put("errorMsg", "Compte incorrect !");
            	   System.out.println("Compte incorrect (MAIL)!");
            	   return "Login";
               }
               MD5Hasher hasher = new MD5Hasher();
	           String passwordhashed = hasher.hash(password);
               if (!loginpassword.equals(passwordhashed)) {
            	   model.put("errorMsg", "Compte incorrect !");
            	   System.out.println("Compte incorrect (MDP)!");
            	   return "Login";
               }              
            db.close();
            }                     
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            model.put("errorMsg", "Compte incorrect !");
            return "Login";
        }
		session.setAttribute("Nom",name);
		String redirectUrl = String.format("/accueil");
		
		return "redirect:" + redirectUrl;
	}
	@GetMapping("/register")
	public String register() {
		return "Register";
	}
	@PostMapping("/register")
	public String registered(ModelMap model, @RequestParam String email, @RequestParam String username,@RequestParam String password, @RequestParam String passwordconfirm) {
		if (password.equals(passwordconfirm)) {
			try {
	            DataBaseAccess db = new DataBaseAccess();
	            
	            // Exécution d'une requête SELECT
	            String sqlSelect = "SELECT mail,name FROM Users";
	            ResultSet resultSelect = db.executeQuery(sqlSelect);
	            while (resultSelect.next()) {
	                String mail = resultSelect.getString("mail");
	                String name = resultSelect.getString("name");
	               if (email.equals(mail) || username.equals(name)) {
	            	   model.put("errorMsg", "L'adresse mail ou le nom d'utilisateur existe déjà !");
	            	   return "Register";
	               }
	            }
	            MD5Hasher hasher = new MD5Hasher();
	            String passwordhashed = hasher.hash(password);
	            String sqlInsert = "INSERT INTO Users VALUES (null, '"+username+"','"+email+"','"+passwordhashed+"',"+"'1')";
	            int rowsInserted = db.executeUpdate(sqlInsert);
	            System.out.println(rowsInserted + " lignes ont été insérées.");
	            
	            db.close();
	        } catch (SQLException e) {
	            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
	        }
			model.put("AcceptMsg", "Vous êtes correctement inscrit à BigCloud ! Vous pouvez désormais vous connecter");
			try {
			    SshTunnel sshConnector = new SshTunnel("bot.nightjs.ovh", "root", "toutnwar619!");
			    String output = sshConnector.executeCommand("mkdir /var/BigCloud/"+username);
			    System.out.println(output);
			    sshConnector.disconnect();
			} catch (JSchException | IOException e) {
			    e.printStackTrace();
			}

			return "redirect:/login";
		}
		
		model.put("errorMsg", "Les mots de passe ne sont pas identiques");
		return "Register";
	}
	@GetMapping("/upload")
    public String upload(HttpSession session, ModelMap model) {
		String nom = (String) session.getAttribute("Nom");
		System.out.println(nom);
    	return "Upload";
    }
    
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, ModelMap model,HttpSession sessionhttp) throws IOException, JSchException, SftpException {
      if (!file.isEmpty()) {
    	String nom = (String) sessionhttp.getAttribute("Nom");
    	sessionhttp.setAttribute("Nom",nom);
    	System.out.println(nom);
        String localFilePath = file.getOriginalFilename();
        String remoteFilePath = "/var/BigCloud/" +nom+"/"+file.getOriginalFilename();
        byte[] bytes = file.getBytes();
        Path path = Paths.get(localFilePath);
        Files.write(path, bytes);
        
        JSch jsch = new JSch();
        Session session = jsch.getSession("root", "bot.nightjs.ovh", 22);
        session.setPassword("toutnwar619!");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        channelSftp.put(localFilePath, remoteFilePath);
        channelSftp.disconnect();
        session.disconnect();
        
        //sshFileTransferService.sendFileOverSsh("root", "toutnwar619!", "bot.nightjs.ovh", 3306, localFilePath, remoteFilePath);
        return "redirect:/accueil";
      } else {
    	  model.put("errorMsg", "Erreur d'upload");
        return "Upload";
      }
    }
    
    @GetMapping("/success")
    public String success() {
    	return "Success";
    }
}
