package com.jibug.frpc.common.model;

import java.io.Serializable;

/**
 * @author heyingcai
 */
public class FrpcRequest implements Serializable {

    /**
     * 请求消息id
     */
    private String requestId;

    /**
     * 请求的具体的类名，接口名
     */
    private String className;

    /**
     * 请求的具体的方法名
     */
    private String methodName;

    /**
     * 请求的参数类型
     */
    private Class<?>[] parameterTypes;

    /**
     * 请求的参数
     */
    private Object[] parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

}
