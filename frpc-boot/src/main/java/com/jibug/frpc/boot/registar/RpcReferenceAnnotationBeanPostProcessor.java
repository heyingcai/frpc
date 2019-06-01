package com.jibug.frpc.boot.registar;

import com.jibug.frpc.boot.registar.annotation.RpcReferenceFieldElement;
import com.jibug.frpc.common.annotation.RpcReference;
import com.jibug.frpc.common.cluster.registry.Registry;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyingcai
 */
public class RpcReferenceAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements InitializingBean, PriorityOrdered, ApplicationContextAware {

    public static final String BEAN_NAME = "rpcReferenceAnnotationBeanPostProcessor";

    private int order = Ordered.LOWEST_PRECEDENCE - 2;

    private ApplicationContext applicationContext;

    private Registry registry;

    private final Map<String, InjectionMetadata> injectionMetadataCache = new ConcurrentHashMap<>(256);

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        InjectionMetadata referenceMetadata = findReferenceMetadata(beanName, bean.getClass(), pvs);
        try {
            referenceMetadata.inject(bean, beanName, pvs);
        } catch (BeanCreationException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of @RpcReference dependencies failed", ex);
        }
        return pvs;
    }

    private InjectionMetadata findReferenceMetadata(String beanName, Class<?> clazz, PropertyValues pvs) {
        String cacheKey = (StringUtils.hasLength(beanName) ? beanName : clazz.getName());
        InjectionMetadata injectionMetadata = injectionMetadataCache.get(cacheKey);
        if (InjectionMetadata.needsRefresh(injectionMetadata, clazz)) {
            synchronized (this.injectionMetadataCache) {
                injectionMetadata = injectionMetadataCache.get(cacheKey);
                if (InjectionMetadata.needsRefresh(injectionMetadata, clazz)) {
                    if (injectionMetadata != null) {
                        injectionMetadata.clear(pvs);
                    }
                    try {
                        injectionMetadata = buildReferenceMetadata(clazz);
                        this.injectionMetadataCache.put(cacheKey, injectionMetadata);
                    } catch (NoClassDefFoundError err) {
                        throw new IllegalStateException("Failed to introspect bean class [" + clazz.getName() +
                                "] for reference metadata: could not find class that it depends on", err);
                    }
                }
            }
        }
        return injectionMetadata;
    }

    private InjectionMetadata buildReferenceMetadata(final Class<?> clazz) {
        return new InjectionMetadata(clazz, findFieldReferenceMetadata(clazz));
    }


    private List<InjectionMetadata.InjectedElement> findFieldReferenceMetadata(final Class<?> beanClass) {
        final List<InjectionMetadata.InjectedElement> elements = new LinkedList<>();
        ReflectionUtils.doWithFields(beanClass, field -> {
            RpcReference rpcReference = field.getAnnotation(RpcReference.class);
            if (rpcReference != null) {
                if (Modifier.isStatic(field.getModifiers())) {
                    return;
                }
                elements.add(new RpcReferenceFieldElement(field, rpcReference, registry));
            }
        });
        return elements;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        registry = (Registry) applicationContext.getBean(FrpcRegistrar.REGISTRY_CENTER);
    }
}
