package com.jibug.frpc.common.cluster.registry;

import com.jibug.frpc.common.config.ConsumerConfig;
import com.jibug.frpc.common.config.ProviderConfig;
import com.jibug.frpc.common.config.RegistryConfig;

import java.util.List;

/**
 * @author heyingcai
 */
public class ConsulRegistry extends Registry {

    protected ConsulRegistry(RegistryConfig registryConfig) {
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
    public List<ProviderInfo> subscribe(ConsumerConfig config) {
        return null;
    }

    @Override
    public void unSubscribe(ConsumerConfig config) {

    }
}
