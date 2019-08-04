package com.jibug.frpc.net.support;

import com.jibug.frpc.common.cluster.registry.ProviderInfo;
import com.jibug.frpc.common.cluster.registry.Registry;
import com.jibug.frpc.common.config.ConsumerConfig;
import com.jibug.frpc.common.exception.FrpcRuntimeException;
import com.jibug.frpc.common.model.FrpcRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author heyingcai
 */
public class RandomLoadBalancer extends AbstractLoadBalancer {
    @Override
    public ProviderInfo select(FrpcRequest request, ConsumerConfig consumerConfig, Registry registry) {
        String directAddr = consumerConfig.getDirectAddr();
        if (StringUtils.isNotBlank(directAddr)) {
            String[] address = directAddr.split(":");
            return new ProviderInfo(address[0], Integer.parseInt(address[1]));
        } else {
            List<ProviderInfo> providerInfos = registry.subscribe(consumerConfig);
            if (providerInfos.size() == 0) {
                throw new FrpcRuntimeException("Subscribe server info failed");
            }
            int index = ThreadLocalRandom.current().nextInt() * providerInfos.size();
            return providerInfos.get(index % providerInfos.size());
        }
    }
}
