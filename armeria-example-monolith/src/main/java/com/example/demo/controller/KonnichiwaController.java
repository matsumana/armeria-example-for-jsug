package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.grpc.Hello.HelloReply;
import com.example.demo.grpc.Hello.HelloRequest;
import com.example.demo.grpc.HelloServiceGrpc.HelloServiceBlockingStub;

@RestController
public class KonnichiwaController {

    private final HelloServiceBlockingStub stub;

    public KonnichiwaController(HelloServiceBlockingStub stub) {
        this.stub = stub;
    }

    @GetMapping("/konnichiwa/{name}")
    String konnichiwa(@PathVariable String name) {
        final HelloRequest request = HelloRequest.newBuilder()
                                                 .setName(name)
                                                 .build();
        final HelloReply reply = stub.hello(request);
        return reply.getMessage();
    }
}
