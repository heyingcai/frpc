package com.jibug.frpc.boot.config;

import com.jibug.frpc.common.constant.ConfigPropertiesKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author heyingcai
 */
@Configuration
@ComponentScan(basePackages = {"com.jibug"})
public class FrpcConfig {

    @Autowired
    private ConfigurableEnvironment env;

    @Bean
    public FrpcProperties frpcProperties() {
        FrpcProperties frpcProperties = new FrpcProperties();
        frpcProperties.setRegistryProperties(getRegistryProperties());
        return frpcProperties;
    }


    private RegistryProperties getRegistryProperties() {
        String address = env.getProperty(ConfigPropertiesKey.REGISTRY_ADDRESS);
        String protocol = env.getProperty(ConfigPropertiesKey.REGISTRY_PROTOCOL);
        return new RegistryProperties(protocol, address);
    }
}
