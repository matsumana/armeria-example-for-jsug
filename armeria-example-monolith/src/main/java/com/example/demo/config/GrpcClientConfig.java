package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.grpc.HelloServiceGrpc;
import com.example.demo.grpc.HelloServiceGrpc.HelloServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Configuration
public class GrpcClientConfig {

    private static final String HOSTNAME_MICROSERVICE1 = "localhost";
    private static final int HTTP_PORT_MICROSERVICE1 = 8081;

    @Bean
    HelloServiceBlockingStub helloServiceBlockingStub() {
        final ManagedChannel channel = ManagedChannelBuilder.forAddress(HOSTNAME_MICROSERVICE1,
                                                                        HTTP_PORT_MICROSERVICE1)
                                                            .usePlaintext()
                                                            .build();
        return HelloServiceGrpc.newBlockingStub(channel);
    }
}
