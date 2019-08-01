package com.jibug.frpc.net.support;

import com.jibug.frpc.common.cluster.enums.RequestType;
import com.jibug.frpc.common.cluster.registry.ProviderInfo;
import com.jibug.frpc.common.model.FrpcRequest;
import com.jibug.frpc.net.client.RpcClient;

/**
 * @author heyingcai
 */
public class FailoverHaStrategy extends AbstractHaStrategy {

    private RpcClient rpcClient;

    public FailoverHaStrategy(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    @Override
    public Object remoteCall(FrpcRequest request, RequestType requestType, ProviderInfo providerInfo) {
        return null;
    }
}
