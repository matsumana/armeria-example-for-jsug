package com.example.demo.controller;

import org.springframework.stereotype.Component;

import com.example.demo.grpc.Hola.HolaReply;
import com.example.demo.grpc.Hola.HolaRequest;
import com.example.demo.grpc.HolaServiceGrpc.HolaServiceImplBase;

import io.grpc.stub.StreamObserver;

@Component
public class HolaController extends HolaServiceImplBase {

    @Override
    public void hola(HolaRequest request, StreamObserver<HolaReply> responseObserver) {
        final HolaReply response = HolaReply.newBuilder()
                                            .setMessage("Hola, " + request.getName())
                                            .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
