package com.jibug.frpc.common.config;

/**
 * @author heyingcai
 */
public class ProviderConfig<T> extends AbstractConfig {

    private transient T reference;

    private ServerConfig serverConfig;

    private ServiceConfig serviceConfig;

    public T getReference() {
        return reference;
    }

    public void setReference(T reference) {
        this.reference = reference;
    }

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
