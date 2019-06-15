package com.jibug.frpc.common.cluster.support;

import com.jibug.frpc.common.cluster.registry.Registry;
import com.jibug.frpc.common.model.FrpcRequest;

/**
 * @author heyingcai
 */
public abstract class AbstractLoadBalancer {

    protected abstract String select(FrpcRequest request, Registry registry);

}
