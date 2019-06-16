package com.jibug.frpc.boot.proxy;

import com.jibug.frpc.common.cluster.enums.LoadBalanceType;
import com.jibug.frpc.common.cluster.registry.Registry;
import com.jibug.frpc.common.cluster.support.AbstractLoadBalancer;
import com.jibug.frpc.common.cluster.support.HashLoadBalancer;
import com.jibug.frpc.common.cluster.support.RandomLoadBalancer;
import com.jibug.frpc.common.cluster.support.RoundRobinLoadBalancer;
import com.jibug.frpc.common.config.MethodConfig;
import com.jibug.frpc.common.config.ServiceConfig;
import com.jibug.frpc.common.model.FrpcRequest;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

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


        loadBalancer.select(new FrpcRequest(method.getDeclaringClass().getName(), serviceConfig.getServiceName(), methodName, method.getParameterTypes(), arguments), registry);
        return null;
    }
}
