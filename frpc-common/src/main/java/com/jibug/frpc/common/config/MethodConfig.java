package com.jibug.frpc.common.config;

import com.jibug.frpc.common.cluster.enums.RequestType;
import com.jibug.frpc.common.codec.compress.CompressEnum;
import com.jibug.frpc.common.codec.serialize.SerializeProtocolEnum;

/**
 * @author heyingcai
 */
public class MethodConfig {

    private String methodName;

    private int timeout;

    private CompressEnum compressType;

    private SerializeProtocolEnum serializeProtocol;

    private RequestType requestType;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public CompressEnum getCompressType() {
        return compressType;
    }

    public void setCompressType(CompressEnum compressType) {
        this.compressType = compressType;
    }

    public SerializeProtocolEnum getSerializeProtocol() {
        return serializeProtocol;
    }

    public void setSerializeProtocol(SerializeProtocolEnum serializeProtocol) {
        this.serializeProtocol = serializeProtocol;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
}
