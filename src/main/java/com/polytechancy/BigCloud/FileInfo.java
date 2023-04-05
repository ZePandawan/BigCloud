package com.polytechancy.BigCloud;
import java.io.File;
import java.text.SimpleDateFormat;
import java.io.File;
import java.text.SimpleDateFormat;

public class FileInfo {
    public static class FileInformation {
        private long fileSize;
        private String creationTime;
        private String lastModifiedTime;

        public FileInformation(long fileSize, String creationTime, String lastModifiedTime) {
            this.fileSize = fileSize;
            this.creationTime = creationTime;
            this.lastModifiedTime = lastModifiedTime;
        }

        public long getFileSize() {
            return fileSize;
        }

        public String getCreationTime() {
            return creationTime;
        }

        public String getLastModifiedTime() {
            return lastModifiedTime;
        }
    }

    public static FileInformation getFileInfo(String filePath) {
        File file = new File(filePath);

        // Récupération de la taille du fichier
        long fileSize = file.length();

        // Récupération de la date de création du fichier
        long creationTime = file.lastModified();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedCreationTime = dateFormat.format(creationTime);

        // Récupération de la date de dernière modification du fichier
        long lastModifiedTime = file.lastModified();
        String formattedLastModifiedTime = dateFormat.format(lastModifiedTime);

        return new FileInformation(fileSize, formattedCreationTime, formattedLastModifiedTime);
    }
}
