package com.jibug.frpc.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

import static com.jibug.frpc.common.config.RpcServerConfig.getIntValue;
import static com.jibug.frpc.common.config.RpcServerConfig.getStringValue;
import static com.jibug.frpc.common.constant.ConfigPropertiesKey.*;

/**
 * @author heyingcai
 */
@Configuration
@ComponentScan(basePackages = {"com.jibug"})
public class FrpcConfig {

    @Autowired
    private ConfigurableEnvironment env;

    @Bean
    @Order(value = 1)
    public FrpcProperties frpcProperties() {
        FrpcProperties frpcProperties = new FrpcProperties();
        frpcProperties.setRegistryProperties(getRegistryProperties());
        frpcProperties.setServerProperties(getServerProperties());
        return frpcProperties;
    }

    private ServerProperties getServerProperties() {
        String host = env.getProperty(SERVER_HOST, String.valueOf(getStringValue(SERVER_HOST)));
        Integer port = Integer.valueOf(env.getProperty(SERVER_PORT, String.valueOf(getIntValue(SERVER_PORT))));
        String contextPath = env.getProperty(SERVER_CONTEXT_PATH, getStringValue(SERVER_CONTEXT_PATH));
        Integer threadPoolCore = Integer.valueOf(env.getProperty(SERVER_THREAD_POOL_CORE, String.valueOf(getIntValue(SERVER_THREAD_POOL_CORE))));
        Integer threadPoolMax = Integer.valueOf(env.getProperty(SERVER_THREAD_POOL_MAX, String.valueOf(getIntValue(SERVER_THREAD_POOL_MAX))));
        return new ServerProperties(host, port, contextPath, threadPoolCore, threadPoolMax);
    }

    private RegistryProperties getRegistryProperties() {
        String address = env.getProperty(REGISTRY_ADDRESS, getStringValue(REGISTRY_ADDRESS));
        String protocol = env.getProperty(REGISTRY_PROTOCOL, getStringValue(REGISTRY_PROTOCOL));
        String username = env.getProperty(REGISTRY_USERNAME, getStringValue(REGISTRY_USERNAME));
        String password = env.getProperty(REGISTRY_PASSWORD, getStringValue(REGISTRY_PASSWORD));
        return new RegistryProperties(protocol, address, username, password);
    }


}
