package com.jibug.frpc.common.cluster.registry;

import com.jibug.frpc.common.config.ConsumerConfig;
import com.jibug.frpc.common.config.ProviderConfig;
import com.jibug.frpc.common.config.RegistryConfig;

/**
 * @author heyingcai
 */
public abstract class Registry {

    protected RegistryConfig registryConfig;

    protected Registry(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }

    public abstract void init();

    public abstract boolean start();

    public abstract void register(ProviderConfig config);

    public abstract void unRegister(ProviderConfig config);

    public abstract void subscribe(ConsumerConfig config);

    public abstract void unSubscribe(ConsumerConfig config);

}
