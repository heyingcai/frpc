package com.jibug.frpc.net.handler;

import com.jibug.frpc.common.model.FrpcRequest;
import com.jibug.frpc.common.model.FrpcRequestBody;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author heyingcai
 * @date 2019-07-29 17:38
 */
public class ServerProcessHandler extends SimpleChannelInboundHandler<FrpcRequest<FrpcRequestBody>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FrpcRequest<FrpcRequestBody> frpcRequestBodyFrpcRequest) throws Exception {

    }
}
