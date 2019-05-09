package com.jibug.frpc.net.client;

import com.jibug.frpc.net.request.RequestType;

/**
 * @author heyingcai
 */
public abstract class Client {

    public abstract void init();

    public abstract void connect();

    public abstract void send(RequestType requestType);
}
