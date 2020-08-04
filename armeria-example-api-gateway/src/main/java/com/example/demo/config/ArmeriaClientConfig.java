package com.example.demo.config;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.grpc.HelloServiceGrpc.HelloServiceFutureStub;
import com.example.demo.grpc.HolaServiceGrpc.HolaServiceFutureStub;
import com.example.demo.retrofit.HelloClient;

import com.linecorp.armeria.client.ClientDecoration;
import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.armeria.client.ClientOptions;
import com.linecorp.armeria.client.Clients;
import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retrofit2.ArmeriaRetrofit;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Configuration
public class ArmeriaClientConfig {

    private static final String BASE_URL_MONOLITH = "http://localhost:8080/";
    private static final String BASE_URL_MICROSERVICE1 = "gproto+http://localhost:8081/";
    private static final String BASE_URL_MICROSERVICE2 = "gproto+http://localhost:8082/";

    @Bean
    public ClientFactory clientFactory(PrometheusMeterRegistry registry) {
        // Collect Armeria client metrics
        return ClientFactory.builder()
                            .meterRegistry(registry)
                            .build();
    }

    @Bean
    Retrofit retrofit(ClientFactory clientFactory) {
        final WebClient webClient = WebClient.builder(BASE_URL_MONOLITH)
                                             .factory(clientFactory)
                                             .build();
        final ClientDecoration decoration =
                ClientDecoration.builder()
                                .add(LoggingClient.builder()
                                                  .logger(LoggerFactory.getLogger(HelloClient.class))
                                                  .newDecorator())
                                .build();
        return ArmeriaRetrofit.builder(webClient)
//                              .addConverterFactory(JacksonConverterFactory.create())  // for JSON
                              .addConverterFactory(ScalarsConverterFactory.create())  // for text
                              .option(ClientOptions.DECORATION.newValue(decoration))
                              .build();
    }

    @Bean
    HelloServiceFutureStub helloServiceFutureStub(ClientFactory clientFactory) {
        return Clients.builder(BASE_URL_MICROSERVICE1)
                      .factory(clientFactory)
                      .decorator(LoggingClient.builder()
                                              .logger(LoggerFactory.getLogger(HelloServiceFutureStub.class))
                                              .newDecorator())
                      .build(HelloServiceFutureStub.class);
    }

    @Bean
    HolaServiceFutureStub holaServiceFutureStub(ClientFactory clientFactory) {
        return Clients.builder(BASE_URL_MICROSERVICE2)
                      .factory(clientFactory)
                      .decorator(LoggingClient.builder()
                                              .logger(LoggerFactory.getLogger(HolaServiceFutureStub.class))
                                              .newDecorator())
                      .build(HolaServiceFutureStub.class);
    }
}
