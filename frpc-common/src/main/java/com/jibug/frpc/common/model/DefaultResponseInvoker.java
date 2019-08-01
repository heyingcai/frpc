package com.jibug.frpc.common.model;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author heyingcai
 */
public class DefaultResponseInvoker implements Invoker<FrpcResponse> {

    private final long contextId;

    private volatile FrpcResponse response;

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    public DefaultResponseInvoker(long contextId) {
        this.contextId = contextId;
    }

    @Override
    public FrpcResponse waitResult(long timeoutMillis) throws InterruptedException {
        countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
        return response;
    }

    @Override
    public FrpcResponse waitResult() throws InterruptedException {
        countDownLatch.await();
        return response;
    }

    @Override
    public void putResult(FrpcResponse response) {
        this.response = response;
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
