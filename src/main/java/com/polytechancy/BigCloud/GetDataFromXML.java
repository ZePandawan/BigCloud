package com.polytechancy.BigCloud;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class GetDataFromXML {
    private String host_ssh;
    private String user_ssh;
    private String password_ssh;
    private String host_bdd;
    private String user_bdd;
    private String password_bdd;
    public void readXmlFile(ResourceLoader resourceLoader) throws IOException, SAXException, ParserConfigurationException {
        Resource resource = resourceLoader.getResource("classpath:static/config.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(resource.getInputStream());
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("datas");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                this.host_ssh = element.getElementsByTagName("host_ssh").item(0).getTextContent();
                this.user_ssh = element.getElementsByTagName("user_ssh").item(0).getTextContent();
                this.password_ssh = element.getElementsByTagName("password_ssh").item(0).getTextContent();
                this.host_bdd = element.getElementsByTagName("host_bdd").item(0).getTextContent();
                this.user_bdd = element.getElementsByTagName("user_bdd").item(0).getTextContent();
                this.password_bdd = element.getElementsByTagName("password_bdd").item(0).getTextContent();
            }
        }
    }

    public String getUser_bdd() {
        return user_bdd;
    }

    public String getPassword_ssh() {
        return password_ssh;
    }

    public String getPassword_bdd() {
        return password_bdd;
    }

    public String getUser_ssh() {
        return user_ssh;
    }

    public String getHost_ssh() {
        return host_ssh;
    }

    public String getHost_bdd() {
        return host_bdd;
    }
}
