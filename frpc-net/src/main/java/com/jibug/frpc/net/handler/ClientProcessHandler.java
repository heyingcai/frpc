package com.jibug.frpc.net.handler;

import com.jibug.frpc.common.model.FrpcResponse;
import com.jibug.frpc.common.model.Invoker;
import com.jibug.frpc.net.Connection;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author heyingcai
 */
public class ClientProcessHandler extends SimpleChannelInboundHandler<FrpcResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FrpcResponse msg) throws Exception {
        Connection connection = ctx.channel().attr(Connection.CONNECTION).get();
        Invoker invoker = connection.removeInvoker(msg.getRequestId());
        if (invoker != null) {
            invoker.putResult(msg);
        }
    }
}
