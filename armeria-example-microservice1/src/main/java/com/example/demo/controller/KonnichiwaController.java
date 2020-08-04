package com.example.demo.controller;

import org.springframework.stereotype.Component;

import com.example.demo.grpc.Hello.HelloReply;
import com.example.demo.grpc.Hello.HelloRequest;
import com.example.demo.grpc.HelloServiceGrpc.HelloServiceImplBase;

import io.grpc.stub.StreamObserver;

@Component
public class KonnichiwaController extends HelloServiceImplBase {

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        final HelloReply response = HelloReply.newBuilder()
                                              .setMessage("こんにちは, " + request.getName())
                                              .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
