package com.jibug.frpc.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author heyingcai
 */
@ConfigurationProperties(prefix = "frpc")
public class FrpcProperties {

    private RegistryProperties registryProperties;

    private ServerProperties serverProperties;

    public RegistryProperties getRegistryProperties() {
        return registryProperties;
    }

    public void setRegistryProperties(RegistryProperties registryProperties) {
        this.registryProperties = registryProperties;
    }

    public ServerProperties getServerProperties() {
        return serverProperties;
    }

    public void setServerProperties(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }
}
