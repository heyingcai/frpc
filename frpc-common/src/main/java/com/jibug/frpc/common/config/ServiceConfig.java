package com.jibug.frpc.common.config;

import com.jibug.frpc.common.cluster.enums.HaStrategyType;
import com.jibug.frpc.common.cluster.enums.LoadBalanceType;
import com.jibug.frpc.common.codec.compress.CompressEnum;
import com.jibug.frpc.common.codec.serialize.SerializeProtocolEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyingcai
 */
public class ServiceConfig {

    private String serverName;

    private String host;

    private int port;

    private String serviceName;

    private int timeout;

    private CompressEnum compressEnum;

    private SerializeProtocolEnum protocolEnum;

    private HaStrategyType haStrategyType;

    private LoadBalanceType loadBalanceType;

    private Map<String,MethodConfig> methodConfigMap = new ConcurrentHashMap<>();

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public CompressEnum getCompressEnum() {
        return compressEnum;
    }

    public void setCompressEnum(CompressEnum compressEnum) {
        this.compressEnum = compressEnum;
    }

    public SerializeProtocolEnum getProtocolEnum() {
        return protocolEnum;
    }

    public void setProtocolEnum(SerializeProtocolEnum protocolEnum) {
        this.protocolEnum = protocolEnum;
    }

    public HaStrategyType getHaStrategyType() {
        return haStrategyType;
    }

    public void setHaStrategyType(HaStrategyType haStrategyType) {
        this.haStrategyType = haStrategyType;
    }

    public LoadBalanceType getLoadBalanceType() {
        return loadBalanceType;
    }

    public void setLoadBalanceType(LoadBalanceType loadBalanceType) {
        this.loadBalanceType = loadBalanceType;
    }

    public Map<String, MethodConfig> getMethodConfigMap() {
        return methodConfigMap;
    }

    public void setMethodConfigMap(Map<String, MethodConfig> methodConfigMap) {
        this.methodConfigMap = methodConfigMap;
    }
}
