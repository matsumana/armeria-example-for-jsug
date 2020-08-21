package com.example.demo.config;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.controller.HolaController;
import com.example.demo.grpc.Hola.HolaRequest;
import com.example.demo.grpc.HolaServiceGrpc;

import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import com.linecorp.armeria.spring.DocServiceConfigurator;

@Configuration
public class ArmeriaServerConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(HolaController holaController) {
        return serverBuilder -> serverBuilder
                .service(GrpcService.builder()
                                    .addService(holaController)
                                    .supportedSerializationFormats(GrpcSerializationFormats.values())
                                    .enableUnframedRequests(true)
                                    .build(),
                         LoggingService.builder()
                                       .logger(LoggerFactory.getLogger(HolaController.class))
                                       .newDecorator());
    }

    @Bean
    public DocServiceConfigurator docServiceConfigurator() {
        return docServiceBuilder ->
                docServiceBuilder.exampleRequests(HolaServiceGrpc.SERVICE_NAME,
                                                  "Hola",
                                                  HolaRequest.newBuilder()
                                                             .setName("Armeria")
                                                             .build());
    }
}
