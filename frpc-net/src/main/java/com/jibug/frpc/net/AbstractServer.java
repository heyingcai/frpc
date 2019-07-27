package com.jibug.frpc.net;

/**
 * @author heyingcai
 */
public abstract class AbstractServer {

    public abstract void doInit();

    public abstract void doStart() throws InterruptedException;

    public abstract void doStop();
}
