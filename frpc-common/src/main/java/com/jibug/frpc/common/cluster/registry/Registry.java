package com.jibug.frpc.common.cluster.registry;

import com.jibug.frpc.common.config.ConsumerConfig;
import com.jibug.frpc.common.config.RegistryConfig;

/**
 * @author heyingcai
 */
public abstract class Registry {

    public abstract void init();

    public abstract boolean start();

    public abstract void register(RegistryConfig config);

    public abstract void unRegister(RegistryConfig config);

    public abstract void subscribe(ConsumerConfig config);

    public abstract void unSubscribe(ConsumerConfig config);

}
