package com.jibug.frpc.net.client;

import com.jibug.frpc.common.model.FrpcRequest;
import com.jibug.frpc.common.model.FrpcResponse;
import com.jibug.frpc.common.model.Invoker;
import com.jibug.frpc.net.Connection;
import com.jibug.frpc.net.NettyConnectionPool;

/**
 * @author heyingcai
 */
public class RpcClient extends AbstractRemoting {

    private NettyConnectionPool connectionPool;

    public RpcClient() {
        connectionPool = NettyConnectionPool.getInstance();
    }

    public void oneway(String address, FrpcRequest request) {
        Connection connection = connectionPool.getObject(address);
        this.oneway(connection, request);
    }

    public FrpcRequest<FrpcResponse> sync(String address, FrpcRequest request, long timeoutMillis) throws InterruptedException {
        Connection connection = connectionPool.getObject(address);
        return this.sync(connection, request, timeoutMillis);
    }

    public FrpcRequest<FrpcResponse> sync(String address, FrpcRequest request) throws InterruptedException {
        Connection connection = connectionPool.getObject(address);
        try {
            return this.sync(connection, request);
        } finally {
            connectionPool.restore(address, connection);
        }
    }

    public Invoker async(String address, FrpcRequest request) {
        Connection connection = connectionPool.getObject(address);
        return this.async(connection, request);
    }

}
