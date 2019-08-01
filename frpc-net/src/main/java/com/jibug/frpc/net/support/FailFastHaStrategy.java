package com.jibug.frpc.net.support;

import com.jibug.frpc.common.cluster.enums.RequestType;
import com.jibug.frpc.common.cluster.registry.ProviderInfo;
import com.jibug.frpc.common.model.FrpcRequest;
import com.jibug.frpc.net.client.RpcClient;

/**
 * @author heyingcai
 */
public class FailFastHaStrategy extends AbstractHaStrategy {

    private RpcClient rpcClient;

    public FailFastHaStrategy(RpcClient remoting) {
        this.rpcClient = rpcClient;
    }

    @Override
    public Object remoteCall(FrpcRequest request, RequestType requestType, ProviderInfo providerInfo) {
        final String address = providerInfo.getUrl() == null ? providerInfo.getHost() + ":" + providerInfo.getPort() : providerInfo.getUrl();
        switch (requestType) {
            case ONEWAY:
                rpcClient.oneway(address, request);
                return null;
            case SYNC:
                try {
                    return rpcClient.sync(address, request, 5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return null;
                }
            case FUTURE:
                return rpcClient.async(address, request);
            default:
                try {
                    return rpcClient.sync(address, request, 5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return null;
                }
        }
    }
}
