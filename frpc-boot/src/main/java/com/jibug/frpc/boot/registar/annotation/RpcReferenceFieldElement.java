package com.jibug.frpc.boot.registar.annotation;

import com.jibug.frpc.boot.proxy.RpcMethodInterceptor;
import com.jibug.frpc.common.annotation.RpcReference;
import com.jibug.frpc.common.cluster.registry.Registry;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

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
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addInterface(referenceClass);
        proxyFactory.addAdvice(new RpcMethodInterceptor());
        proxyFactory.setOptimize(false);
        Object proxy = proxyFactory.getProxy();
        ReflectionUtils.makeAccessible(field);
        field.set(target, proxy);
    }
}
