package com.jibug.frpc.boot.proxy;

import com.jibug.frpc.common.config.ServiceConfig;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author heyingcai
 */
public class RpcMethodInterceptor implements MethodInterceptor {

    private ServiceConfig serviceConfig;

    public RpcMethodInterceptor() {
    }

    public RpcMethodInterceptor(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Object[] arguments = methodInvocation.getArguments();


        return null;
    }
}
