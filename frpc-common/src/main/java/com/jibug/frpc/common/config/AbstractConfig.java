package com.jibug.frpc.common.config;

import java.util.List;

/**
 * @author heyingcai
 */
public abstract class AbstractConfig {

    protected String interfaceId;

    protected List<RegistryConfig> registry;

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public List<RegistryConfig> getRegistry() {
        return registry;
    }

    public void setRegistry(List<RegistryConfig> registry) {
        this.registry = registry;
    }
}
