package com.jibug.frpc.net.client;

/**
 * @author heyingcai
 */
public abstract class Client {

    public abstract void init();

    public abstract void connect();

    public abstract void send(RequestType requestType);
}
