package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.grpc.HelloServiceGrpc.HelloServiceFutureStub;

import com.linecorp.armeria.client.ClientDecoration;
import com.linecorp.armeria.client.ClientOption;
import com.linecorp.armeria.client.Clients;
import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retrofit2.ArmeriaRetrofit;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Configuration
public class ArmeriaClientConfig {

    private static final String BASE_URL_PROJECT1 = "http://localhost:8080/";
    private static final String BASE_URL_PROJECT2 = "gproto+h2c://localhost:8081/";
    private static final Logger logger = LoggerFactory.getLogger(ArmeriaClientConfig.class);

    @Bean
    Retrofit retrofit() {
        final WebClient webClient = WebClient.of(BASE_URL_PROJECT1);
        final ClientDecoration decoration = ClientDecoration.builder()
                                                            .add(LoggingClient.builder()
                                                                              .logger(logger)
                                                                              .newDecorator())
                                                            .build();
        return ArmeriaRetrofit.builder(webClient)
//                              .addConverterFactory(JacksonConverterFactory.create())
                              .addConverterFactory(ScalarsConverterFactory.create())
                              .option(ClientOption.DECORATION.newValue(decoration))
                              .build();
    }

    @Bean
    HelloServiceFutureStub konnichiwaService() {
        return Clients.builder(BASE_URL_PROJECT2)
                      .decorator(LoggingClient.builder()
                                              .logger(logger)
                                              .newDecorator())
                      .build(HelloServiceFutureStub.class);
    }
}
