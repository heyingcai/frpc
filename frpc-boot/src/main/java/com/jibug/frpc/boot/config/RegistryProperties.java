package com.jibug.frpc.boot.config;

/**
 * @author heyingcai
 */
public class RegistryProperties {

    private String protocol;

    private String address;

    public RegistryProperties() {
    }

    public RegistryProperties(String protocol, String address) {
        this.protocol = protocol;
        this.address = address;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
