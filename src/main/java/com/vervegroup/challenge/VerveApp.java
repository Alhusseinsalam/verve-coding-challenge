package com.vervegroup.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VerveApp {

    public static void main(String[] args) {
        SpringApplication.run(VerveApp.class, args);
    }

}
