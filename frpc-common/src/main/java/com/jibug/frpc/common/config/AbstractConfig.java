package com.jibug.frpc.common.config;

/**
 * @author heyingcai
 */
public abstract class AbstractConfig {

    protected String interfaceId;

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

}
