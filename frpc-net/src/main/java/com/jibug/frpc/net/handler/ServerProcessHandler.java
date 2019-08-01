package com.jibug.frpc.net.handler;

import com.jibug.frpc.common.model.FrpcRequest;
import com.jibug.frpc.common.model.FrpcRequestBody;
import com.jibug.frpc.net.server.ServerProcessTask;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author heyingcai
 * @date 2019-07-29 17:38
 */
public class ServerProcessHandler extends SimpleChannelInboundHandler<FrpcRequest<FrpcRequestBody>> {

    protected ThreadPoolExecutor serviceThreadPool;

    public ServerProcessHandler(ThreadPoolExecutor serviceThreadPool) {
        this.serviceThreadPool = serviceThreadPool;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FrpcRequest<FrpcRequestBody> request) throws Exception {
        serviceThreadPool.submit(new ServerProcessTask(request, channelHandlerContext));
    }
}
