package com.jibug.frpc.boot.proxy;

import com.jibug.frpc.common.cluster.enums.LoadBalanceType;
import com.jibug.frpc.common.cluster.support.AbstractLoadBalancer;
import com.jibug.frpc.common.cluster.support.HashLoadBalancer;
import com.jibug.frpc.common.cluster.support.RandomLoadBalancer;
import com.jibug.frpc.common.cluster.support.RoundRobinLoadBalancer;
import com.jibug.frpc.common.config.MethodConfig;
import com.jibug.frpc.common.config.ServiceConfig;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author heyingcai
 */
public class RpcMethodInterceptor implements MethodInterceptor {

    private ServiceConfig serviceConfig;

    private AbstractLoadBalancer loadBalancer;


    public RpcMethodInterceptor() {

    }

    public RpcMethodInterceptor(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;

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

        Map<String, MethodConfig> methodConfigMap = serviceConfig.getMethodConfigMap();


        return null;
    }
}
