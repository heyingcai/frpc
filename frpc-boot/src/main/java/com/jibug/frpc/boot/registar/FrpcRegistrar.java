package com.jibug.frpc.boot.registar;

import com.jibug.frpc.boot.annotation.EnableFrpc;
import com.jibug.frpc.common.cluster.registry.RegistryProtocolEnum;
import com.jibug.frpc.common.config.RegistryConfig;
import com.jibug.frpc.common.constant.ConfigPropertiesKey;
import com.jibug.frpc.common.exception.FrpRuntimeException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public static final String REGISTRY_CENTER = "registryCenter";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        registerRpcReferenceAnnotationBeanPostProcessor(registry);
        registerResolveRpcServiceAnnotationBean(registry);
    }

    private void registerRpcReferenceAnnotationBeanPostProcessor(BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(RpcReferenceAnnotationBeanPostProcessor.class);
        BeanDefinitionHolder holder = new BeanDefinitionHolder(definition.getBeanDefinition(), RpcReferenceAnnotationBeanPostProcessor.BEAN_NAME,
                new String[]{RpcReferenceAnnotationBeanPostProcessor.BEAN_NAME});
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }

    private void registerResolveRpcServiceAnnotationBean(BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(RpcServiceProxyBean.class);
        BeanDefinitionHolder holder = new BeanDefinitionHolder(definition.getBeanDefinition(), RpcServiceProxyBean.BEAN_NAME,
                new String[]{RpcServiceProxyBean.BEAN_NAME});

        definition.addPropertyValue("registryConfig", registerRegistryConfig(registry));
        definition.setInitMethodName("start");
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
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

    private RegistryConfig registerRegistryConfig(BeanDefinitionRegistry registry) {
        String address = environment.getProperty(ConfigPropertiesKey.REGISTRY_ADDRESS);
        String protocol = environment.getProperty(ConfigPropertiesKey.REGISTRY_PROTOCOL);
        Class<?> registryClass = RegistryProtocolEnum.getClassTypeByName(protocol);
        if (registryClass == null) {
            throw new FrpRuntimeException("The " + protocol + " registry protocol not found, please check the properties.");
        }
        RegistryConfig registryConfig = new RegistryConfig(protocol, address);
        BeanDefinitionBuilder registryDefinition = BeanDefinitionBuilder.genericBeanDefinition(registryClass);
        registryDefinition.addConstructorArgValue(registryConfig);
        registryDefinition.setInitMethodName("start");
        BeanDefinitionHolder holder = new BeanDefinitionHolder(registryDefinition.getBeanDefinition(), REGISTRY_CENTER,
                new String[]{REGISTRY_CENTER});
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
        return registryConfig;
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
