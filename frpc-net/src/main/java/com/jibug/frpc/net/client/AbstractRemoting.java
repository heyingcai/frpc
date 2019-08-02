package com.jibug.frpc.net.client;

import com.jibug.frpc.common.constant.ConfigConstants;
import com.jibug.frpc.common.model.DefaultResponseInvoker;
import com.jibug.frpc.common.model.FrpcRequest;
import com.jibug.frpc.common.model.FrpcRequestHeader;
import com.jibug.frpc.common.model.FrpcResponse;
import com.jibug.frpc.common.model.Invoker;
import com.jibug.frpc.net.Connection;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * @author heyingcai
 */
public abstract class AbstractRemoting {

    public void oneway(Connection connection, FrpcRequest request) {
        connection.getChannel().writeAndFlush(connection);
    }

    public FrpcRequest<FrpcResponse> sync(Connection connection, FrpcRequest request) throws InterruptedException {
        return sync(connection, request, 0);
    }

    public FrpcRequest<FrpcResponse> sync(Connection connection, FrpcRequest request, long timeoutMillis) throws InterruptedException {
        Invoker<FrpcRequest<FrpcResponse>> invoker = new DefaultResponseInvoker(request.getRequestHeader().getRequestId());
        connection.addInvoker(invoker);
        remoteCall(connection, request, invoker);
        FrpcRequest<FrpcResponse> response;
        if (timeoutMillis > 0) {
            response = invoker.waitResult(timeoutMillis);
        }else {
            response = invoker.waitResult();
        }
        if (response == null) {
            connection.removeInvoker(request.getRequestHeader().getRequestId());
            FrpcRequest<FrpcResponse> errorResponse = new FrpcRequest<>(new FrpcRequestHeader(), new FrpcResponse(request.getRequestHeader().getRequestId(), null, ConfigConstants.TIMEOUT_STATUS, "time out"));
            invoker.putResult(errorResponse);
        }
        return response;
    }

    public Invoker async(Connection connection, FrpcRequest request) {
        Invoker<FrpcRequest<FrpcResponse>> invoker = new DefaultResponseInvoker(request.getRequestHeader().getRequestId());
        connection.addInvoker(invoker);
        remoteCall(connection, request, invoker);
        return invoker;
    }

    private void remoteCall(Connection connection, FrpcRequest request, Invoker invoker) {
        try {
            connection.getChannel().writeAndFlush(request).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        connection.removeInvoker(request.getRequestHeader().getRequestId());
                        invoker.putResult(new FrpcResponse(request.getRequestHeader().getRequestId(), null, ConfigConstants.EXCEPTION_STATUS, future.cause().getMessage()));
                    }
                }
            });
        } catch (Exception e) {
            connection.removeInvoker(request.getRequestHeader().getRequestId());
            invoker.putResult(new FrpcResponse(request.getRequestHeader().getRequestId(), null, ConfigConstants.EXCEPTION_STATUS, e.getMessage()));
        }
    }
}
