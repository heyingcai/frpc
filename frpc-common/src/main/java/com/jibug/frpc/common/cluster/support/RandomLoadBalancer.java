package com.jibug.frpc.common.cluster.support;

import com.jibug.frpc.common.cluster.registry.Registry;
import com.jibug.frpc.common.model.FrpcRequest;

/**
 * @author heyingcai
 */
public class RandomLoadBalancer extends AbstractLoadBalancer {
    @Override
    protected String select(FrpcRequest request, Registry registry) {
        return null;
    }
}
