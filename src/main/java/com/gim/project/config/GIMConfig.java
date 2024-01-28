package com.gim.project.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GIMConfig {

    @Value("${username}")
    @Getter private String user;

    @Value("${privateKey}")
    @Getter private String privateKey;

    @Value("${sshPort}")
    @Getter private int sshPort;

    @Value("${localHost}")
    @Getter private String localHost;

    @Value("${localRDFPort}")
    @Getter private int localRDFPort;

    @Value("${remoteHost}")
    @Getter private String remoteHost;

    @Value("${remoteRDFPort}")
    @Getter private int remoteRDFPort;



    public GIMConfig(){
        this.user = "graphuser";
        this.privateKey = "src/main/resources/id_rsa";
        this.sshPort = 31000;
        this.localHost = "129.70.51.28";
        this.localRDFPort = 3030;
        this.remoteHost = "localhost";
        this.remoteRDFPort = 3030;
    }
    @Override
    public String toString() {
        return "GIMConfig{" +
                "user='" + user + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", sshPort=" + sshPort +
                ", localHost='" + localHost + '\'' +
                ", localRDFPort=" + localRDFPort +
                ", remoteHost='" + remoteHost + '\'' +
                ", remoteRDFPort=" + remoteRDFPort +
                '}';
    }

}