package com.example.demo.config;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.controller.HelloController;
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
    public ArmeriaServerConfigurator armeriaServerConfigurator(HelloController helloController) {
        return serverBuilder -> serverBuilder
                .service(GrpcService.builder()
                                    .addService(helloController)
                                    .supportedSerializationFormats(GrpcSerializationFormats.values())
                                    .enableUnframedRequests(true)
                                    .build(),
                         LoggingService.builder()
                                       .logger(LoggerFactory.getLogger(helloController.getClass()))
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
