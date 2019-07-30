package com.jibug.frpc.net.client;

import com.jibug.frpc.net.NettyConnectionPool;

/**
 * @author heyingcai
 */
public class RpcClient {

    private NettyConnectionPool connectionPool;

    public RpcClient() {
        connectionPool = NettyConnectionPool.getInstance();
    }

}
