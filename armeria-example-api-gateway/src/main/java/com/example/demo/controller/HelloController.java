package com.example.demo.controller;

import static com.linecorp.armeria.common.HttpStatus.OK;
import static com.linecorp.armeria.common.MediaType.PLAIN_TEXT_UTF_8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.retrofit.HelloClient;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;

import hu.akarnokd.rxjava2.interop.SingleInterop;
import io.reactivex.Single;
import retrofit2.Retrofit;

@Component
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    private final Retrofit retrofit;

    public HelloController(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Get("/hello/{name}")
    public Single<HttpResponse> hello(@Param String name) {
        final HelloClient helloClient = retrofit.create(HelloClient.class);
        return SingleInterop.fromFuture(helloClient.hello(name))
                            .doOnError(e -> logger.error("Retrofit HelloClient error", e))
                            .map(response -> HttpResponse.of(OK, PLAIN_TEXT_UTF_8, response));
    }
}
