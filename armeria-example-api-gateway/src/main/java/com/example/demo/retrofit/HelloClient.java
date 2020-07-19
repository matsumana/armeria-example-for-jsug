package com.example.demo.retrofit;

import java.util.concurrent.CompletableFuture;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HelloClient {

    @GET("/hello/{name}")
    CompletableFuture<String> hello(@Path("name") String name);
}
