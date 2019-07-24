package com.jibug.frpc.common.cluster.support;

import com.jibug.frpc.common.cluster.enums.RequestType;
import com.jibug.frpc.common.cluster.registry.ProviderInfo;
import com.jibug.frpc.common.model.FrpcRequest;

/**
 * @author heyingcai
 */
public class FailoverHaStrategy extends AbstractHaStrategy {
    @Override
    public Object remoteCall(FrpcRequest request, RequestType requestType, ProviderInfo providerInfo) {
        return null;
    }
}
