package com.jibug.frpc.common.cluster.registry;

import com.jibug.frpc.common.config.ConsumerConfig;
import com.jibug.frpc.common.config.RegistryConfig;

/**
 * @author heyingcai
 */
public class LocalRegistry extends Registry{

    @Override
    public synchronized void init() {

    }

    @Override
    public boolean start() {
        return false;
    }

    @Override
    public void register(RegistryConfig config) {

    }

    @Override
    public void unRegister(RegistryConfig config) {

    }

    @Override
    public void subscribe(ConsumerConfig config) {

    }

    @Override
    public void unSubscribe(ConsumerConfig config) {

    }
}
