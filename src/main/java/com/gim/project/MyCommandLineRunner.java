package com.gim.project;

import com.gim.project.config.GIMConnector;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    private final GIMConnector gimConnector;

    public MyCommandLineRunner(GIMConnector gimConnector) {
        this.gimConnector = gimConnector;
    }

    @Override
    public void run(String... args) throws Exception {
        gimConnector.connect();
    }
}
