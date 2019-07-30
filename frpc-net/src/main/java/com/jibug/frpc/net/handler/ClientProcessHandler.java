package com.jibug.frpc.net.handler;

import com.jibug.frpc.common.model.FrpcRequest;
import com.jibug.frpc.common.model.FrpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author heyingcai
 */
public class ClientProcessHandler extends SimpleChannelInboundHandler<FrpcRequest<FrpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FrpcRequest<FrpcResponse> msg) throws Exception {

    }
}
