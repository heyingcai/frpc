package com.jibug.frpc.net.handler;

import com.jibug.frpc.common.codec.compress.Compress;
import com.jibug.frpc.common.codec.compress.CompressEnum;
import com.jibug.frpc.common.codec.compress.CompressPool;
import com.jibug.frpc.common.codec.serialize.Serialize;
import com.jibug.frpc.common.codec.serialize.SerializePool;
import com.jibug.frpc.common.model.FrpcRequest;
import com.jibug.frpc.common.model.FrpcRequestHeader;
import com.jibug.frpc.common.model.MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author heyingcai
 */
public class RpcEncoder extends MessageToByteEncoder<FrpcRequest> {

    @Override
    protected void encode(ChannelHandlerContext ctx, FrpcRequest msg, ByteBuf out) throws Exception {
        FrpcRequestHeader requestHeader = msg.getRequestHeader();
        Object requestBody = msg.getRequestBody();
        if (requestHeader.getType() == MessageType.HEARTBEAT.getType() && requestBody == null) {
            return;
        }
        out.writeByte(requestHeader.getMagic());
        out.writeByte(requestHeader.getVersion());
        out.writeByte(requestHeader.getCompress());
        out.writeByte(requestHeader.getType());
        out.writeByte(requestHeader.getCodec());
        out.writeLong(requestHeader.getRequestId());

        SerializePool serializePool = SerializePool.getInstance();
        Serialize serialize = serializePool.getObject(requestHeader.getCodec());
        CompressPool compressPool = CompressPool.getInstance();
        Compress compress = null;
        if (requestHeader.getCompress() != CompressEnum.NONE.getValue()) {
            compress = compressPool.getObject(requestHeader.getCompress());
        }
        try {
            byte[] data;
            byte[] bytes = serialize.serialize(requestBody);
            data = compress != null ? compress.compress(bytes) : bytes;
            out.writeInt(data.length);
            out.writeBytes(data);
        } finally {
            serializePool.restore(requestHeader.getCodec(), serialize);
            if (compress != null) {
                compressPool.restore(requestHeader.getCompress(), compress);
            }
        }

    }
}
