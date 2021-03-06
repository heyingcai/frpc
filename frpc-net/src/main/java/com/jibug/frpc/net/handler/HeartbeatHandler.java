package com.jibug.frpc.net.handler;

import com.jibug.frpc.common.model.FrpcRequest;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author heyingcai
 */
@ChannelHandler.Sharable
public class HeartbeatHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            FrpcRequest heartbeatMsg = FrpcRequest.createHeartbeatMsg();
            ctx.writeAndFlush(heartbeatMsg);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
