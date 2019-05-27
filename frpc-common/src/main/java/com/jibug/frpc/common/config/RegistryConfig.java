package com.jibug.frpc.common.config;

import java.util.Map;

import static com.jibug.frpc.common.config.RpcServerConfig.getStringValue;
import static com.jibug.frpc.common.constant.ConfigPropertiesKey.*;

/**
 * @author heyingcai
 */
public class RegistryConfig {

    private String protocol = getStringValue(REGISTRY_PROTOCOL);

    private String address = getStringValue(REGISTRY_ADDRESS);

    private String username = getStringValue(REGISTRY_USERNAME);

    private String password = getStringValue(REGISTRY_PASSWORD);

    private Map<String, String> parameters;

    public RegistryConfig() {
    }

    public RegistryConfig(String protocol, String address) {
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

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
