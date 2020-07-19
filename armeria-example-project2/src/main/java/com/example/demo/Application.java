package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Runtime.getRuntime()
               .addShutdownHook(new Thread(() -> logger.info("Shutting down the application")));

        SpringApplication.run(Application.class, args);
    }
}
