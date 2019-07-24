package com.jibug.frpc.common.model;

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
}
