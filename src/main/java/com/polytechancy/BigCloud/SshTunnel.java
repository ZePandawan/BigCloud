package com.polytechancy.BigCloud;

import java.io.IOException;
import java.io.InputStream;
import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class SshTunnel {

    private Session session;
    private String host;
    private String user;
    private String password;
    
    public SshTunnel(String host, String user, String password) throws JSchException {
        this.host = host;
        this.user = user;
        this.password = password;
        
        JSch jsch = new JSch();
        this.session = jsch.getSession(user, host, 22);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
    }
    
    public String executeCommand(String command) throws IOException, JSchException {
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);

        InputStream commandOutput = channel.getInputStream();
        channel.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(commandOutput));
        StringBuilder output = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        reader.close();
        channel.disconnect();
        
        return output.toString();
    }
    
    public void disconnect() {
        session.disconnect();
    }
    
    public String getHost() {
        return host;
    }
    
    public String getUser() {
        return user;
    }
    
    public String getPassword() {
        return password;
    }

}

