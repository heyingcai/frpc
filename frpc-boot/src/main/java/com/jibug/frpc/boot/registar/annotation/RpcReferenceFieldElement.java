package com.jibug.frpc.boot.registar.annotation;

import com.jibug.frpc.common.annotation.RpcReference;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.InjectionMetadata;

import java.lang.reflect.Field;

/**
 * @author heyingcai
 */
public class RpcReferenceFieldElement extends InjectionMetadata.InjectedElement {

    private final Field field;

    private final RpcReference rpcReference;
    private volatile RpcReferenceBean<?> rpcReferenceBean;

    protected RpcReferenceFieldElement(Field field, RpcReference rpcReference) {
        super(field, null);
        this.field = field;
        this.rpcReference = rpcReference;
    }

    @Override
    protected void inject(Object target, String requestingBeanName, PropertyValues pvs) throws Throwable {
        Class<?> referenceClass = field.getType();

    }
}
