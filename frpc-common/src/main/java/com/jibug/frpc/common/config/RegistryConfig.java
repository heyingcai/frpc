package com.jibug.frpc.common.config;

import java.util.Map;

/**
 * @author heyingcai
 */
public class RegistryConfig {

    private String protocol;

    private String address;

    private String username;

    private String password;

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
