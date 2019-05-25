package com.jibug.frpc.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author heyingcai
 */
@ConfigurationProperties(prefix = "frpc")
public class FrpcProperties {

    private RegistryProperties registryProperties;


    public RegistryProperties getRegistryProperties() {
        return registryProperties;
    }

    public void setRegistryProperties(RegistryProperties registryProperties) {
        this.registryProperties = registryProperties;
    }
}
