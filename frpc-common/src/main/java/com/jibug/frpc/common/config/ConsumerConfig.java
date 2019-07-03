package com.jibug.frpc.common.config;

import static com.jibug.frpc.common.config.RpcServerConfig.getStringValue;
import static com.jibug.frpc.common.constant.ConfigConstants.DEFAULT_TIMEOUT;
import static com.jibug.frpc.common.constant.ConfigPropertiesKey.SERVER_PROTOCOL;

/**
 * @author heyingcai
 */
public class ConsumerConfig extends AbstractConfig {

    private String protocol = getStringValue(SERVER_PROTOCOL);

    private String directAddr;

    private int connectTimeout = DEFAULT_TIMEOUT;

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
