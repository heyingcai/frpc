package com.jibug.frpc.net.support;

import com.jibug.frpc.common.cluster.enums.RequestType;
import com.jibug.frpc.common.cluster.registry.ProviderInfo;
import com.jibug.frpc.common.model.FrpcRequest;

/**
 * @author heyingcai
 */
public abstract class AbstractHaStrategy {

    /**
     * 远程调用
     *
     * @param request
     * @param requestType
     * @param providerInfo
     * @return
     */
    public abstract Object remoteCall(FrpcRequest request, RequestType requestType, ProviderInfo providerInfo);

}
