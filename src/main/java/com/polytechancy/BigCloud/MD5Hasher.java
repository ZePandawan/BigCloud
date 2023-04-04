package com.polytechancy.BigCloud;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hasher {

    public String hash(String input) {
        try {
            // Créer une instance de MessageDigest avec l'algorithme MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Convertir la chaîne en tableau de bytes et hasher
            byte[] hash = md.digest(input.getBytes());

            // Convertir le hash en hexadécimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Cette exception ne devrait pas se produire si l'algorithme MD5 est supporté
            throw new RuntimeException("MD5 n'est pas supporté", e);
        }
    }
}