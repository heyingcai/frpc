package com.jibug.frpc.boot.proxy;

import com.jibug.frpc.common.cluster.enums.LoadBalanceType;
import com.jibug.frpc.common.cluster.registry.ProviderInfo;
import com.jibug.frpc.common.cluster.registry.Registry;
import com.jibug.frpc.common.cluster.support.AbstractLoadBalancer;
import com.jibug.frpc.common.cluster.support.HashLoadBalancer;
import com.jibug.frpc.common.cluster.support.RandomLoadBalancer;
import com.jibug.frpc.common.cluster.support.RoundRobinLoadBalancer;
import com.jibug.frpc.common.config.ConsumerConfig;
import com.jibug.frpc.common.config.MethodConfig;
import com.jibug.frpc.common.config.ServiceConfig;
import com.jibug.frpc.common.model.FrpcRequest;
import com.jibug.frpc.common.model.FrpcRequestBody;
import com.jibug.frpc.common.model.FrpcRequestHeader;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * @author heyingcai
 */
public class RpcMethodInterceptor implements MethodInterceptor {

    private ServiceConfig serviceConfig;

    private AbstractLoadBalancer loadBalancer;

    private Registry registry;


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
            case RANDOM:
                loadBalancer = new RandomLoadBalancer();
                break;
            case ROUND_ROBIN:
                loadBalancer = new RoundRobinLoadBalancer();
                break;
            default:
                loadBalancer = new RandomLoadBalancer();
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

        FrpcRequestHeader requestHeader = new FrpcRequestHeader();
        FrpcRequestBody requestBody = new FrpcRequestBody(method.getDeclaringClass().getName(), serviceConfig.getServiceName(), methodName, method.getParameterTypes(), arguments);

        ProviderInfo providerInfo = loadBalancer.select(new FrpcRequest(requestHeader, requestBody), resolveConsumerConfig(), registry);

        return null;
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
