package com.jibug.frpc.common.config;

/**
 * @author heyingcai
 */
public class ProviderConfig extends AbstractConfig {

    private ServerConfig serverConfig;

    private ServiceConfig serviceConfig;

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public ServiceConfig getServiceConfig() {
        return serviceConfig;
    }

    public void setServiceConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }
}
