package com.jibug.frpc.net.support;

import com.jibug.frpc.common.cluster.registry.ProviderInfo;
import com.jibug.frpc.common.cluster.registry.Registry;
import com.jibug.frpc.common.config.ConsumerConfig;
import com.jibug.frpc.common.model.FrpcRequest;

/**
 * @author heyingcai
 */
public class HashLoadBalancer extends AbstractLoadBalancer {
    @Override
    public ProviderInfo select(FrpcRequest request, ConsumerConfig consumerConfig, Registry registry) {
        return null;
    }
}
