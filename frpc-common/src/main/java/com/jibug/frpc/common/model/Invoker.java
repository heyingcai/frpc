package com.jibug.frpc.common.model;

/**
 * @author heyingcai
 */
public interface Invoker<T> {

    T waitResult(long timeoutMillis) throws InterruptedException;

    T waitResult() throws InterruptedException;

    void putResult(T t);

    long contextId();

    boolean isDone();

}
