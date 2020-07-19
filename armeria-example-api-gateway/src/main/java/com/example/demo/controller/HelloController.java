package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.retrofit.HelloClient;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;

import hu.akarnokd.rxjava2.interop.MaybeInterop;
import io.reactivex.Maybe;
import retrofit2.Retrofit;

@Component
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    private final Retrofit retrofit;

    public HelloController(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Get("/hello/:name")
    public Maybe<HttpResponse> hello(@Param String name) {
        final HelloClient helloClient = retrofit.create(HelloClient.class);
        return MaybeInterop.fromFuture(helloClient.hello(name))
                           .doOnError(e -> logger.error("Retrofit HelloClient error", e))
                           .map(message -> HttpResponse.of(HttpStatus.OK, MediaType.JSON_UTF_8, message));
    }
}
