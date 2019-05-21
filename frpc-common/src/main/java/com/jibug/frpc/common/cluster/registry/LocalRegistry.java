package com.jibug.frpc.common.cluster.registry;

import com.jibug.frpc.common.config.ConsumerConfig;
import com.jibug.frpc.common.config.ProviderConfig;
import com.jibug.frpc.common.config.RegistryConfig;

/**
 * @author heyingcai
 */
public class LocalRegistry extends Registry{

    protected LocalRegistry(RegistryConfig registryConfig) {
        super(registryConfig);
    }

    @Override
    public synchronized void init() {

    }

    @Override
    public boolean start() {
        return false;
    }

    @Override
    public void register(ProviderConfig config) {

    }

    @Override
    public void unRegister(ProviderConfig config) {

    }


    @Override
    public void subscribe(ConsumerConfig config) {

    }

    @Override
    public void unSubscribe(ConsumerConfig config) {

    }
}
