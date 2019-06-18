package com.jibug.frpc.common.cluster.support;

import com.jibug.frpc.common.cluster.registry.Registry;
import com.jibug.frpc.common.model.FrpcRequestBody;

/**
 * @author heyingcai
 */
public class HashLoadBalancer extends AbstractLoadBalancer {
    @Override
    public String select(FrpcRequestBody request, Registry registry) {
        return null;
    }
}
