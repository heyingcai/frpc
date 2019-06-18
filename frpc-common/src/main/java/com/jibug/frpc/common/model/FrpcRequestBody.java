package com.jibug.frpc.common.model;

import com.jibug.frpc.common.util.SnowflakeIdWorker;

import java.io.Serializable;

/**
 * @author heyingcai
 */
public class FrpcRequestBody implements Serializable {

    /**
     * 请求消息id
     */
    private String requestId;

    /**
     * 请求的具体的类名，接口名
     */
    private String className;

    /**
     * 服务名
     */
    private String serviceName;

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

    public FrpcRequestBody() {
        this.requestId = String.valueOf(SnowflakeIdWorker.getInstance().nextId());
    }

    public FrpcRequestBody(String className, String serviceName, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        this.className = className;
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.parameters = parameters;
    }

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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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
