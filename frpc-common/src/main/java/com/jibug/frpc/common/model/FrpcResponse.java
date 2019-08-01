package com.jibug.frpc.common.model;

import java.io.Serializable;

/**
 * @author heyingcai
 */
public class FrpcResponse implements Serializable {

    private static final long serialVersionUID = -768874658726107188L;

    /**
     * 请求消息id
     */
    private Long requestId;

    /**
     * 响应结果
     */
    private Object result;

    /**
     * 响应状态
     */
    private Integer status;

    /**
     * 错误结果
     */
    private String errMsg = "";

    public FrpcResponse() {
    }

    public FrpcResponse(Long requestId, Object result, Integer status, String errMsg) {
        this.requestId = requestId;
        this.result = result;
        this.status = status;
        this.errMsg = errMsg;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
