package com.jibug.frpc.net;

/**
 * @author heyingcai
 */
public abstract class ConnectionFactory {

    public abstract void init();

    public abstract Connection connect(String ip, int port);

}
