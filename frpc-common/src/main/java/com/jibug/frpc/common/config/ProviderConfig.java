package com.jibug.frpc.common.config;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyingcai
 */
public class ProviderConfig<T> extends AbstractConfig {

    private transient T reference;

    private ServerConfig serverConfig;

    private Map<Method, MethodConfig> methodConfig = new ConcurrentHashMap<>();

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

    public Map<Method, MethodConfig> getMethodConfig() {
        return methodConfig;
    }

    public void setMethodConfig(Map<Method, MethodConfig> methodConfig) {
        this.methodConfig = methodConfig;
    }
}
