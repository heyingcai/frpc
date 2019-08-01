package com.jibug.frpc.boot.proxy;

import com.jibug.frpc.common.cluster.enums.HaStrategyType;
import com.jibug.frpc.common.cluster.enums.LoadBalanceType;
import com.jibug.frpc.common.cluster.enums.RequestType;
import com.jibug.frpc.common.cluster.registry.ProviderInfo;
import com.jibug.frpc.common.cluster.registry.Registry;
import com.jibug.frpc.net.client.RpcClient;
import com.jibug.frpc.net.support.AbstractHaStrategy;
import com.jibug.frpc.net.support.AbstractLoadBalancer;
import com.jibug.frpc.net.support.FailFastHaStrategy;
import com.jibug.frpc.net.support.FailoverHaStrategy;
import com.jibug.frpc.net.support.HashLoadBalancer;
import com.jibug.frpc.net.support.RandomLoadBalancer;
import com.jibug.frpc.net.support.RoundRobinLoadBalancer;
import com.jibug.frpc.common.codec.compress.CompressEnum;
import com.jibug.frpc.common.codec.serialize.SerializeProtocolEnum;
import com.jibug.frpc.common.config.ConsumerConfig;
import com.jibug.frpc.common.config.MethodConfig;
import com.jibug.frpc.common.config.ServiceConfig;
import com.jibug.frpc.common.constant.ConfigConstants;
import com.jibug.frpc.common.model.FrpcRequest;
import com.jibug.frpc.common.model.FrpcRequestBody;
import com.jibug.frpc.common.model.FrpcRequestHeader;
import com.jibug.frpc.common.model.MessageType;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyingcai
 */
public class RpcMethodInterceptor implements MethodInterceptor {

    private ServiceConfig serviceConfig;

    private AbstractLoadBalancer loadBalancer;

    private AbstractHaStrategy haStrategy;

    private Registry registry;

    public Map<Method, FrpcRequestHeader> headerMapCache = new ConcurrentHashMap<>();

    public RpcMethodInterceptor() {
    }

    public RpcMethodInterceptor(ServiceConfig serviceConfig, Registry registry) {
        this.serviceConfig = serviceConfig;
        this.registry = registry;

        LoadBalanceType loadBalanceType = serviceConfig.getLoadBalanceType();
        switch (loadBalanceType) {
            case HASH:
                loadBalancer = new HashLoadBalancer();
                break;
            case ROUND_ROBIN:
                loadBalancer = new RoundRobinLoadBalancer();
                break;
            case RANDOM:
            default:
                loadBalancer = new RandomLoadBalancer();
                break;
        }
        HaStrategyType haStrategyType = serviceConfig.getHaStrategyType();
        switch (haStrategyType) {
            case FAIL_OVER:
                haStrategy = new FailoverHaStrategy(new RpcClient());
                break;
            default:
                haStrategy = new FailFastHaStrategy(new RpcClient());
                break;
        }
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Object[] arguments = methodInvocation.getArguments();

        String methodName;
        MethodConfig methodConfig = serviceConfig.getMethodConfigMap().get(method.getName());
        if (methodConfig == null) {
            methodName = method.getName();
        } else {
            methodName = methodConfig.getMethodName();
        }

        FrpcRequestHeader requestHeader = headerMapCache.get(method);
        if (requestHeader == null) {
            byte compress = methodConfig != null ? methodConfig.getCompressType().getValue() : CompressEnum.NONE.getValue();
            byte codec = methodConfig != null ? methodConfig.getSerializeProtocol().getValue() : SerializeProtocolEnum.JDK_SERIALIZE.getValue();
            requestHeader = new FrpcRequestHeader(ConfigConstants.PROTOCOL_MAGIC, ConfigConstants.PROTOCOL_VERSION, compress, codec, MessageType.REQUEST.getType());
            headerMapCache.put(method, requestHeader);
        }

        FrpcRequestBody requestBody = new FrpcRequestBody(method.getDeclaringClass().getName(), serviceConfig.getServiceName(), methodName, method.getParameterTypes(), arguments);

        ProviderInfo providerInfo = loadBalancer.select(new FrpcRequest<>(requestHeader, requestBody), resolveConsumerConfig(), registry);

        RequestType requestType = methodConfig != null ? methodConfig.getRequestType() : RequestType.SYNC;

        return haStrategy.remoteCall(new FrpcRequest<>(requestHeader, requestBody), requestType, providerInfo);
    }

    private ConsumerConfig resolveConsumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setConnectTimeout(serviceConfig.getTimeout());
        consumerConfig.setInterfaceId(serviceConfig.getServiceName());
        if (StringUtils.isNotBlank(serviceConfig.getHost()) && serviceConfig.getPort() != 0) {
            consumerConfig.setDirectAddr(serviceConfig.getHost() + ":" + serviceConfig.getPort());
        }
        return consumerConfig;
    }
}
