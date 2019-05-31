package com.jibug.frpc.boot.registar;

import com.jibug.frpc.common.annotation.RpcReference;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Modifier;

/**
 * @author heyingcai
 */
@Component
public class RpcReferenceAnnotaionBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements PriorityOrdered {

    private int order = Ordered.LOWEST_PRECEDENCE - 2;

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), field -> {
            if (field.isAnnotationPresent(RpcReference.class)) {
                RpcReference rpcReference = field.getAnnotation(RpcReference.class);
                if (rpcReference != null) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        return;
                    }
                }

            }
        });
        return super.postProcessAfterInstantiation(bean, beanName);
    }

}
