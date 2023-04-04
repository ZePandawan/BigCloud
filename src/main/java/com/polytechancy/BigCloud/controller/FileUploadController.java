package com.polytechancy.BigCloud.controller;

import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class FileUploadController {
	@Service
	public class SshFileTransferService {
	    
	    public void sendFileOverSsh(String username, String password, String hostname, int port, String localFilePath, String remoteFilePath) throws JSchException, SftpException {
	        JSch jsch = new JSch();
	        Session session = jsch.getSession(username, hostname, port);
	        session.setPassword(password);
	        session.setConfig("StrictHostKeyChecking", "no");
	        session.connect();
	        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
	        channelSftp.connect();
	        channelSftp.put(localFilePath, remoteFilePath);
	        channelSftp.disconnect();
	        session.disconnect();
	    }
	}
}