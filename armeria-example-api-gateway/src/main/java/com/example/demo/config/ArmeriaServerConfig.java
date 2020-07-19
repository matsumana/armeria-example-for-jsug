package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.controller.HelloController;
import com.example.demo.controller.KonnichiwaController;

import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaServerConfig {

    private static final Logger logger = LoggerFactory.getLogger(ArmeriaServerConfig.class);

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(HelloController helloController,
                                                               KonnichiwaController konnichiwaController) {
        return serverBuilder -> serverBuilder
                // helloHandler
                .annotatedService()
                .decorator(LoggingService.builder()
                                         .logger(logger)
                                         .newDecorator())
                .build(helloController)
                // konnichiwaController
                .annotatedService()
                .decorator(LoggingService.builder()
                                         .logger(logger)
                                         .newDecorator())
                .build(konnichiwaController);
    }
}
