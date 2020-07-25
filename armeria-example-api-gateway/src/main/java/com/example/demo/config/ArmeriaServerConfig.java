package com.example.demo.config;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.controller.HelloController;
import com.example.demo.controller.KonnichiwaController;

import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaServerConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(HelloController helloController,
                                                               KonnichiwaController konnichiwaController) {
        return serverBuilder -> serverBuilder
                // HelloController
                .annotatedService()
                .decorator(LoggingService.builder()
                                         .logger(LoggerFactory.getLogger(HelloController.class))
                                         .newDecorator())
                .build(helloController)
                // KonnichiwaController
                .annotatedService()
                .decorator(LoggingService.builder()
                                         .logger(LoggerFactory.getLogger(KonnichiwaController.class))
                                         .newDecorator())
                .build(konnichiwaController);
    }
}
