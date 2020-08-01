package com.example.demo.config;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.controller.HelloController;
import com.example.demo.controller.HolaController;
import com.example.demo.controller.KonnichiwaController;
import com.example.demo.controller.RootController;

import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaServerConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(RootController rootController,
                                                               HelloController helloController,
                                                               KonnichiwaController konnichiwaController,
                                                               HolaController holaController) {
        return serverBuilder -> serverBuilder
                // RootController
                .annotatedService()
                .decorator(LoggingService.builder()
                                         .logger(LoggerFactory.getLogger(RootController.class))
                                         .newDecorator())
                .build(rootController)
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
                .build(konnichiwaController)
                // HolaController
                .annotatedService()
                .decorator(LoggingService.builder()
                                         .logger(LoggerFactory.getLogger(HolaController.class))
                                         .newDecorator())
                .build(holaController);
    }
}
