package com.jibug.frpc.common.config;

import com.jibug.frpc.common.cluster.enums.HaStrategyType;
import com.jibug.frpc.common.cluster.enums.LoadBalanceType;

/**
 * @author heyingcai
 */
public class ConsumerConfig<T> extends AbstractConfig {

    private String protocol;

    private String directAddr;

    private int connectTimeout;

    private HaStrategyType haStrategyType;

    private LoadBalanceType loadBalanceType;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getDirectAddr() {
        return directAddr;
    }

    public void setDirectAddr(String directAddr) {
        this.directAddr = directAddr;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
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
}
