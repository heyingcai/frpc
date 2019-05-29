package com.jibug.frpc.boot.registar;

import com.jibug.frpc.boot.annotation.EnableFrpc;
import com.jibug.frpc.boot.proxy.RpcMethodInterceptor;
import com.jibug.frpc.common.annotation.RpcReference;
import com.jibug.frpc.common.annotation.RpcService;
import com.jibug.frpc.common.cluster.registry.RegistryProtocolEnum;
import com.jibug.frpc.common.config.RegistryConfig;
import com.jibug.frpc.common.constant.ConfigPropertiesKey;
import com.jibug.frpc.common.exception.FrpRuntimeException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author heyingcai
 */
public class FrpcRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware, BeanClassLoaderAware {

    static {
        Resource resource = new ClassPathResource("logo.txt");
        if (resource.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("  __");
            System.out.println(" / _|");
            System.out.println("| |_ _ __ _ __   ___");
            System.out.println("|  _| '__| '_ \\ / __|");
            System.out.println("| | | |  | |_) | (__");
            System.out.println("|_| |_|  | .__/ \\___|");
            System.out.println("         | |");
            System.out.println("         |_|");
        }
        System.out.println("Frpc is running ...");
        System.out.println("Author: heyingcai. Email: admin@jibug.com");
    }

    private Environment environment;

    private ClassLoader classLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider provider = componentScanner();
        provider.addIncludeFilter(new AnnotationTypeFilter(RpcReference.class));
        Set<String> basePackages = resolveBasePackages(annotationMetadata);

        for (String basePackage : basePackages) {
            registerRpcReferenceProxyBean(basePackage, getAnnotationFilterScanner(RpcReference.class), registry);
            registerRpcService(basePackage, getAnnotationFilterScanner(RpcService.class), registry);
        }

        registerRegistryConfig(registry);
    }

    private ClassPathScanningCandidateComponentProvider getAnnotationFilterScanner(Class<? extends Annotation> annotationType) {
        ClassPathScanningCandidateComponentProvider provider = componentScanner();
        provider.addIncludeFilter(new AnnotationTypeFilter(annotationType));
        return provider;
    }

    protected ClassPathScanningCandidateComponentProvider componentScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(
                    AnnotatedBeanDefinition beanDefinition) {
                if (beanDefinition.getMetadata().isIndependent()) {

                    if (beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().getInterfaceNames().length == 1
                            && Annotation.class.getName().equals(beanDefinition.getMetadata().getInterfaceNames()[0])) {
                        try {
                            Class<?> target = ClassUtils.forName(beanDefinition.getMetadata().getClassName(), FrpcRegistrar.this.classLoader);
                            return !target.isAnnotation();
                        } catch (Exception ex) {
                            logger.error("Could not load target class: " + beanDefinition.getMetadata().getClassName(), ex);
                        }
                    }
                    return true;
                }
                return false;

            }
        };
    }

    private Set<String> resolveBasePackages(AnnotationMetadata annotationMetadata) {
        Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(EnableFrpc.class.getName());
        String[] basePackages = (String[]) attributes.get("basePackages");
        Set<String> packages = new HashSet<>();
        for (String basePackage : basePackages) {
            packages.add(basePackage);
        }

        if (packages.isEmpty()) {
            packages.add(ClassUtils.getPackageName(annotationMetadata.getClassName()));
        }
        return packages;
    }

    private void registerRpcReferenceProxyBean(String basePackage, ClassPathScanningCandidateComponentProvider provider, BeanDefinitionRegistry registry) {
        registerRpcReferenceProxyBean(provider.findCandidateComponents(basePackage), registry);
    }

    private void registerRpcReferenceProxyBean(Set<BeanDefinition> beanDefinitions, BeanDefinitionRegistry registry) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (beanDefinition instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
                AnnotationMetadata metadata = annotatedBeanDefinition.getMetadata();
                String className = metadata.getClassName();
                BeanDefinitionBuilder definition = BeanDefinitionBuilder
                        .genericBeanDefinition(RpcReferenceDelegate.class);
                Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(RpcReference.class.getName());

                definition.addPropertyValue("serverName", annotationAttributes.get("serverName"));
                definition.addPropertyValue("host", annotationAttributes.get("host"));
                definition.addPropertyValue("port", annotationAttributes.get("port"));
                definition.addPropertyValue("serviceName", annotationAttributes.get("serviceName"));
                definition.addPropertyValue("protocol", annotationAttributes.get("protocol"));
                definition.addPropertyValue("compress", annotationAttributes.get("compress"));
                definition.addPropertyValue("haStrategyType", annotationAttributes.get("haStrategyType"));
                definition.addPropertyValue("loadBalanceType", annotationAttributes.get("loadBalanceType"));
                definition.addPropertyValue("timeout", annotationAttributes.get("timeout"));
                definition.addPropertyValue("interceptor", new RpcMethodInterceptor());
                try {
                    definition.addPropertyValue("interfaceName", Class.forName(className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                BeanDefinitionHolder holder = new BeanDefinitionHolder(definition.getBeanDefinition(), className,
                        new String[]{className});
                BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
            }
        }
    }

    private void registerRpcService(String basePackage, ClassPathScanningCandidateComponentProvider provider, BeanDefinitionRegistry registry) {
        registerRpcService(provider.findCandidateComponents(basePackage), registry);
    }

    private void registerRpcService(Set<BeanDefinition> beanDefinitions, BeanDefinitionRegistry registry) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (beanDefinition instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
                AnnotationMetadata metadata = annotatedBeanDefinition.getMetadata();
                String superClassName = metadata.getSuperClassName();
            }
        }
    }

    private void registerRegistryConfig(BeanDefinitionRegistry registry) {
        String address = environment.getProperty(ConfigPropertiesKey.REGISTRY_ADDRESS);
        String protocol = environment.getProperty(ConfigPropertiesKey.REGISTRY_PROTOCOL);
        Class<?> registryClass = RegistryProtocolEnum.getClassTypeByName(protocol);
        if (registryClass == null) {
            throw new FrpRuntimeException("The " + protocol + " registry protocol not found, please check the properties.");
        }
        RegistryConfig registryConfig = new RegistryConfig(protocol, address);
        BeanDefinitionBuilder registryDefinition = BeanDefinitionBuilder.genericBeanDefinition(registryClass);
        String className = registryClass.getCanonicalName();
        registryDefinition.addConstructorArgValue(registryConfig);
        registryDefinition.setInitMethodName("start");
        BeanDefinitionHolder holder = new BeanDefinitionHolder(registryDefinition.getBeanDefinition(), className,
                new String[]{className});
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
