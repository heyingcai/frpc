package com.jibug.frpc.common.cluster.registry;

/**
 * @author heyingcai
 */
public abstract class Registry<P,C> {

    public abstract void init();

    public abstract boolean start();

    public abstract void register(P config);

    public abstract void unRegister(C config);

    public abstract void subscribe(C config);

    public abstract void unSubscribe(C config);

}
