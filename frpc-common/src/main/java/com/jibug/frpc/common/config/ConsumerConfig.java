package com.jibug.frpc.common.config;

/**
 * @author heyingcai
 */
public class ConsumerConfig<T> extends AbstractConfig {

    private String protocol;

    private String directAddr;

    private int connectTimeout;

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

}
