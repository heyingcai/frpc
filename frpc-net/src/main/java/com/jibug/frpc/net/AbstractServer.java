package com.jibug.frpc.net;

/**
 * @author heyingcai
 */
public abstract class AbstractServer {

    protected abstract void doInit();

    protected abstract void doStart() throws InterruptedException;

    protected abstract void doStop();
}
