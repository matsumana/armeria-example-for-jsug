package com.example.demo.config;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.controller.KonnichiwaController;
import com.example.demo.controller.RootController;
import com.example.demo.grpc.Hello.HelloRequest;
import com.example.demo.grpc.HelloServiceGrpc;

import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import com.linecorp.armeria.spring.DocServiceConfigurator;

@Configuration
public class ArmeriaServerConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(RootController rootController,
                                                               KonnichiwaController konnichiwaController) {
        return serverBuilder -> serverBuilder
                // RootController
                .annotatedService()
                .decorator(LoggingService.builder()
                                         .logger(LoggerFactory.getLogger(RootController.class))
                                         .newDecorator())
                .build(rootController)
                // KonnichiwaController
                .service(GrpcService.builder()
                                    .addService(konnichiwaController)
                                    .supportedSerializationFormats(GrpcSerializationFormats.values())
                                    .enableUnframedRequests(true)
                                    .build(),
                         LoggingService.builder()
                                       .logger(LoggerFactory.getLogger(KonnichiwaController.class))
                                       .newDecorator());
    }

    @Bean
    public DocServiceConfigurator docServiceConfigurator() {
        return docServiceBuilder ->
                docServiceBuilder.exampleRequestForMethod(HelloServiceGrpc.SERVICE_NAME,
                                                          "Hello",
                                                          HelloRequest.newBuilder()
                                                                      .setName("Armeria")
                                                                      .build());
    }
}
