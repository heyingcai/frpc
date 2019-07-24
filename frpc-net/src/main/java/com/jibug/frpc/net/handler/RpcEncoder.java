package com.jibug.frpc.net.handler;

import com.jibug.frpc.common.model.FrpcRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author heyingcai
 */
public class RpcEncoder extends MessageToByteEncoder<FrpcRequest> {

    @Override
    protected void encode(ChannelHandlerContext ctx, FrpcRequest msg, ByteBuf out) throws Exception {

    }
}
