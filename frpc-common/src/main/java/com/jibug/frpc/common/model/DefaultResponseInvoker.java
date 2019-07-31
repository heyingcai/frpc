package com.jibug.frpc.common.model;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author heyingcai
 */
public class DefaultResponseInvoker implements Invoker<FrpcRequest<FrpcResponse>> {

    private final long contextId;

    private volatile FrpcRequest<FrpcResponse> response;

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    public DefaultResponseInvoker(long contextId) {
        this.contextId = contextId;
    }

    @Override
    public FrpcRequest<FrpcResponse> waitResult(long timeoutMillis) throws InterruptedException {
        countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
        return response;
    }

    @Override
    public FrpcRequest<FrpcResponse> waitResult() throws InterruptedException {
        countDownLatch.await();
        return response;
    }

    @Override
    public void putResult(FrpcRequest<FrpcResponse> frpcResponseFrpcRequest) {
        this.response = frpcResponseFrpcRequest;
    }

    @Override
    public long contextId() {
        return contextId;
    }

    @Override
    public boolean isDone() {
        return countDownLatch.getCount() <= 0;
    }
}
