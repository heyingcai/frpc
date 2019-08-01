package com.jibug.frpc.net.support;

import com.jibug.frpc.common.cluster.registry.ProviderInfo;
import com.jibug.frpc.common.cluster.registry.Registry;
import com.jibug.frpc.common.config.ConsumerConfig;
import com.jibug.frpc.common.model.FrpcRequest;

/**
 * @author heyingcai
 */
public abstract class AbstractLoadBalancer {

    public abstract ProviderInfo select(FrpcRequest request, ConsumerConfig consumerConfig, Registry registry);

}
