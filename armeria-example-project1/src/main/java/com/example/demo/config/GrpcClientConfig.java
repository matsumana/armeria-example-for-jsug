package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.grpc.HelloServiceGrpc;
import com.example.demo.grpc.HelloServiceGrpc.HelloServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Configuration
public class GrpcClientConfig {

    private static final String HOSTNAME_OF_PROJECT2 = "localhost";
    private static final int HTTP_PORT_OF_PROJECT2 = 8081;

    @Bean
    HelloServiceBlockingStub helloServiceBlockingStub() {
        final ManagedChannel channel = ManagedChannelBuilder.forAddress(HOSTNAME_OF_PROJECT2,
                                                                        HTTP_PORT_OF_PROJECT2)
                                                            .usePlaintext()
                                                            .build();
        return HelloServiceGrpc.newBlockingStub(channel);
    }
}
