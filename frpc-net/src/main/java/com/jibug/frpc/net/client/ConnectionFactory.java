package com.jibug.frpc.net.client;

import com.jibug.frpc.net.Connection;

/**
 * @author heyingcai
 */
public abstract class ConnectionFactory {

    public abstract void init();

    public abstract Connection connect(String ip, int port);

}
