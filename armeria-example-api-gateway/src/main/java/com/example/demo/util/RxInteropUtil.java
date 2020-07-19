package com.example.demo.util;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import io.reactivex.Maybe;

public final class RxInteropUtil {

    private RxInteropUtil() {}

    public static <T> Maybe<T> fromListenableFutureToMaybe(ListenableFuture<? extends T> future) {
        return Maybe.defer(() -> Maybe.create(e -> Futures.addCallback(
                future,
                new FutureCallback<T>() {
                    @Override
                    public void onSuccess(T result) {
                        e.onSuccess(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        e.onError(t);
                    }
                },
                MoreExecutors.directExecutor())));
    }
}
