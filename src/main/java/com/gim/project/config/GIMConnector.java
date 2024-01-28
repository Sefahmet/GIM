package com.gim.project.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class GIMConnector {

    @Autowired
    private GIMConfig gimConfig;

    private Session session;

    public void connect() {
        try {

            JSch jsch = new JSch();
            jsch.addIdentity(gimConfig.getPrivateKey());
            System.out.println(gimConfig + gimConfig.getPrivateKey());
            session = jsch.getSession(gimConfig.getUser(), gimConfig.getLocalHost(), gimConfig.getSshPort());
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            try{

                session.setPortForwardingL(gimConfig.getLocalRDFPort(), gimConfig.getRemoteHost(), gimConfig.getRemoteRDFPort());
            }catch (Exception e){
                e.printStackTrace();
            }

            System.out.println("\nThe RDF store (Fuseki) can be accessed under http://" +
                    gimConfig.getRemoteHost() + ":" + gimConfig.getLocalRDFPort());

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI("http://" + gimConfig.getRemoteHost() + ":" + gimConfig.getLocalRDFPort()));
            }

        } catch (JSchException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(gimConfig);
    }

    public void disconnect() {
        if (session != null && session.isConnected()) {
            session.disconnect();
            System.out.println("Connection closed.");
        }
    }

    public Session getSession() {
        return session;
    }

}
