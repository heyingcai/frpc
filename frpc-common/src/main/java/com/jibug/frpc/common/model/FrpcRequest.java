package com.jibug.frpc.common.model;

import com.jibug.frpc.common.codec.compress.CompressEnum;
import com.jibug.frpc.common.codec.serialize.SerializeProtocolEnum;
import com.jibug.frpc.common.constant.ConfigConstants;

import java.io.Serializable;

/**
 * @author heyingcai
 */
public class FrpcRequest<T> implements Serializable {

    private static final long serialVersionUID = -1331504264053595369L;
    private FrpcRequestHeader requestHeader;

    private T requestBody;

    public FrpcRequest() {
    }

    public FrpcRequest(FrpcRequestHeader requestHeader, T requestBody) {
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public FrpcRequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(FrpcRequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public T getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(T requestBody) {
        this.requestBody = requestBody;
    }

    public static FrpcRequest createHeartbeatMsg() {
        return new FrpcRequest<>(new FrpcRequestHeader(ConfigConstants.PROTOCOL_MAGIC, ConfigConstants.PROTOCOL_VERSION,
                CompressEnum.NONE.getValue(), SerializeProtocolEnum.JDK_SERIALIZE.getValue(), MessageType.HEARTBEAT.getType()), null);
    }
}
