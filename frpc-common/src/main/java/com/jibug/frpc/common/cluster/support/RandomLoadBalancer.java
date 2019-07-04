package com.jibug.frpc.common.cluster.support;

import com.jibug.frpc.common.cluster.registry.Registry;
import com.jibug.frpc.common.config.ConsumerConfig;
import com.jibug.frpc.common.model.FrpcRequest;

/**
 * @author heyingcai
 */
public class RandomLoadBalancer extends AbstractLoadBalancer {
    @Override
    public String select(FrpcRequest request, ConsumerConfig consumerConfig, Registry registry) {


        return null;
    }
}
