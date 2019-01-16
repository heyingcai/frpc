package com.jibug.frpc.common.model;

import java.io.Serializable;

/**
 * @author heyingcai
 */
public class FrpcResponse implements Serializable {

    /**
     * 响应消息id
     */
    private String responseId;

    /**
     * 请求消息id
     */
    private String requestId;

    /**
     * 响应结果
     */
    private Object result;

    /**
     * 错误结果
     */
    private String errMsg = "";

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
