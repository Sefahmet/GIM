package com.gim.project;

import com.gim.project.config.GIMConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjectApplication extends ServletInitializer{


    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }




}
