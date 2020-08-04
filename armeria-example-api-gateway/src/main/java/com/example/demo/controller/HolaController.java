package com.example.demo.controller;

import static com.linecorp.armeria.common.HttpStatus.OK;
import static com.linecorp.armeria.common.MediaType.PLAIN_TEXT_UTF_8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.grpc.Hola.HolaReply;
import com.example.demo.grpc.Hola.HolaRequest;
import com.example.demo.grpc.HolaServiceGrpc.HolaServiceFutureStub;
import com.example.demo.util.RxInteropUtil;
import com.google.common.util.concurrent.ListenableFuture;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;

import io.reactivex.Single;

@Component
public class HolaController {

    private static final Logger logger = LoggerFactory.getLogger(HolaController.class);

    private final HolaServiceFutureStub stub;

    public HolaController(HolaServiceFutureStub stub) {
        this.stub = stub;
    }

    @Get("/hola/{name}")
    public Single<HttpResponse> hola(@Param String name) {
        final HolaRequest request = HolaRequest.newBuilder()
                                               .setName(name)
                                               .build();
        final ListenableFuture<HolaReply> future = stub.hola(request);
        return RxInteropUtil.fromListenableFutureToSingle(future)
                            .doOnError(e -> logger.error("gRPC error", e))
                            .map(reply -> HttpResponse.of(OK, PLAIN_TEXT_UTF_8, reply.getMessage()));
    }
}
