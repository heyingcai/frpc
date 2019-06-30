package com.jibug.frpc.common.config;

/**
 * @author heyingcai
 */
public class ProviderConfig extends AbstractConfig {

    private ServerConfig serverConfig;

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }
}
