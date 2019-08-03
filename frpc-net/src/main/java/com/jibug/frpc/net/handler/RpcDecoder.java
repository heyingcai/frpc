package com.jibug.frpc.net.handler;

import com.jibug.frpc.common.codec.compress.Compress;
import com.jibug.frpc.common.codec.compress.CompressEnum;
import com.jibug.frpc.common.codec.compress.CompressPool;
import com.jibug.frpc.common.codec.serialize.Serialize;
import com.jibug.frpc.common.codec.serialize.SerializePool;
import com.jibug.frpc.common.constant.ConfigConstants;
import com.jibug.frpc.common.exception.FrpcRuntimeException;
import com.jibug.frpc.common.model.FrpcRequest;
import com.jibug.frpc.common.model.FrpcRequestBody;
import com.jibug.frpc.common.model.FrpcRequestHeader;
import com.jibug.frpc.common.model.MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author heyingcai
 */
public class RpcDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        byte magic = in.readByte();
        if (magic != ConfigConstants.PROTOCOL_MAGIC) {
            in.resetReaderIndex();
            throw new FrpcRuntimeException("Can not decode the message, magic: " + magic);
        }
        byte version = in.readByte();
        byte compressValue = in.readByte();
        byte type = in.readByte();
        byte codec = in.readByte();
        long requestId = in.readLong();
        int msgSize = in.readInt();
        Object requestBody = null;
        if (msgSize != 0 && MessageType.HEARTBEAT.getType() != type) {
            byte[] body = new byte[msgSize];
            in.readBytes(body);
            SerializePool serializePool = SerializePool.getInstance();
            Serialize serialize = serializePool.getObject(codec);
            CompressPool compressPool = CompressPool.getInstance();
            Compress compress = null;
            if (compressValue != CompressEnum.NONE.getValue()) {
                compress = compressPool.getObject(compressValue);
            }
            try {
                requestBody = serialize.deserialize(compress == null ? body : compress.compress(body), FrpcRequestBody.class);
            } finally {
                serializePool.restore(codec, serialize);
                if (compress != null) {
                    compressPool.restore(compressValue, compress);
                }
            }
        }
        FrpcRequestHeader requestHeader = new FrpcRequestHeader(magic, version, compressValue, type, codec, requestId, msgSize);
        FrpcRequest request = new FrpcRequest(requestHeader, requestBody);
        out.add(request);
    }
}
