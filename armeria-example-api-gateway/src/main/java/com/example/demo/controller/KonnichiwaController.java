package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.grpc.Hello.HelloReply;
import com.example.demo.grpc.Hello.HelloRequest;
import com.example.demo.grpc.HelloServiceGrpc.HelloServiceFutureStub;
import com.example.demo.util.RxInteropUtil;
import com.google.common.util.concurrent.ListenableFuture;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;

import io.reactivex.Single;

@Component
public class KonnichiwaController {

    private static final Logger logger = LoggerFactory.getLogger(KonnichiwaController.class);

    private final HelloServiceFutureStub stub;

    public KonnichiwaController(HelloServiceFutureStub stub) {
        this.stub = stub;
    }

    @Get("/konnichiwa/:name")
    public Single<HttpResponse> konnichiwa(@Param String name) {
        final HelloRequest request = HelloRequest.newBuilder()
                                                 .setName(name)
                                                 .build();
        final ListenableFuture<HelloReply> future = stub.hello(request);
        return RxInteropUtil.fromListenableFutureToSingle(future)
                            .doOnError(e -> logger.error("gRPC error", e))
                            .map(reply -> HttpResponse.of(HttpStatus.OK, MediaType.JSON_UTF_8,
                                                          reply.getMessage()));
    }
}
