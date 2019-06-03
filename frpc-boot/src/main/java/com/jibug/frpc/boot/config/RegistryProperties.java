package com.jibug.frpc.boot.config;

import static com.jibug.frpc.common.config.RpcServerConfig.getStringValue;
import static com.jibug.frpc.common.constant.ConfigPropertiesKey.*;

/**
 * @author heyingcai
 */
public class RegistryProperties {

    private String protocol = getStringValue(REGISTRY_PROTOCOL);

    private String address = getStringValue(REGISTRY_ADDRESS);

    private String username = getStringValue(REGISTRY_USERNAME);

    private String password = getStringValue(REGISTRY_PASSWORD);

    public RegistryProperties() {
    }

    public RegistryProperties(String protocol, String address, String username, String password) {
        this.protocol = protocol;
        this.address = address;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
