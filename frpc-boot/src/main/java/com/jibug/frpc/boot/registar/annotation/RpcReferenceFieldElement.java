package com.jibug.frpc.boot.registar.annotation;

import com.jibug.frpc.boot.proxy.RpcMethodInterceptor;
import com.jibug.frpc.common.annotation.RpcInterface;
import com.jibug.frpc.common.annotation.RpcMethod;
import com.jibug.frpc.common.annotation.RpcReference;
import com.jibug.frpc.common.cluster.registry.Registry;
import com.jibug.frpc.common.config.MethodConfig;
import com.jibug.frpc.common.config.ProviderConfig;
import com.jibug.frpc.common.config.ServiceConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author heyingcai
 */
public class RpcReferenceFieldElement extends InjectionMetadata.InjectedElement {

    private final Field field;

    private final RpcReference rpcReference;

    private final Registry registry;

    public RpcReferenceFieldElement(Field field, RpcReference rpcReference, Registry registry) {
        super(field, null);
        this.field = field;
        this.rpcReference = rpcReference;
        this.registry = registry;
    }


    @Override
    protected void inject(Object target, String requestingBeanName, PropertyValues pvs) throws Throwable {
        Class<?> referenceClass = field.getType();

        RpcInterface rpcInterface = referenceClass.getAnnotation(RpcInterface.class);
        if (rpcInterface == null) {
            return;
        }

        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setCompressEnum(rpcInterface.compress());
        serviceConfig.setHaStrategyType(rpcInterface.haStrategyType());
        serviceConfig.setLoadBalanceType(rpcInterface.loadBalanceType());
        serviceConfig.setHost(rpcInterface.host());
        serviceConfig.setPort(rpcInterface.port());
        serviceConfig.setProtocolEnum(rpcInterface.protocol());
        serviceConfig.setServerName(rpcInterface.serverName());
        serviceConfig.setServiceName(StringUtils.isBlank(rpcInterface.serviceName()) ? referenceClass.getSimpleName() : rpcInterface.serviceName());
        serviceConfig.setTimeout(rpcInterface.timeout());

        for (Method method : referenceClass.getMethods()) {
            RpcMethod rpcMethod = method.getAnnotation(RpcMethod.class);
            if (rpcMethod != null) {
                String methodName = StringUtils.isBlank(rpcMethod.methodName()) ? method.getName() : rpcMethod.methodName();
                MethodConfig methodConfig = new MethodConfig();

                methodConfig.setMethodName(methodName);
                methodConfig.setCompressType(rpcMethod.compress());
                methodConfig.setRequestType(rpcMethod.callType());
                methodConfig.setSerializeProtocol(rpcMethod.serialze());
                methodConfig.setTimeout(rpcMethod.timeout());

                serviceConfig.registerMethodConfig(methodName,methodConfig);
            }
        }

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addInterface(referenceClass);
        proxyFactory.addAdvice(new RpcMethodInterceptor(serviceConfig,registry));
        proxyFactory.setOptimize(false);
        Object proxy = proxyFactory.getProxy();
        ReflectionUtils.makeAccessible(field);
        field.set(target, proxy);
    }
}
