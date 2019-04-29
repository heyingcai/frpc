package com.jibug.frpc.boot.registar;

import com.jibug.frpc.boot.annotation.EnableFrpc;
import com.jibug.frpc.common.annotation.RpcClient;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
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
public class FrpcRegistrar implements ImportBeanDefinitionRegistrar {

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

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(RpcClient.class));
        Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(EnableFrpc.class.getName());

        Set<String> basePackages = resolveBasePackages(annotationMetadata);

        for (String basePackage : basePackages) {
            Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : beanDefinitions) {
                if (beanDefinition instanceof AnnotatedBeanDefinition) {
                    AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;

                }
            }
        }

        System.out.println(attributes);
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

    private void registerRpcClientProxyBean(AnnotatedBeanDefinition annotatedBeanDefinition) {
        AnnotationMetadata metadata = annotatedBeanDefinition.getMetadata();
        String className = metadata.getClassName();
        BeanDefinitionBuilder definition = BeanDefinitionBuilder
                .genericBeanDefinition(RpcClientDelegate.class);
        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(RpcClient.class.getName());

        definition.addPropertyValue("serverName", annotationAttributes.get("serverName"));
        definition.addPropertyValue("host", annotationAttributes.get("host"));
        definition.addPropertyValue("port", annotationAttributes.get("port"));
        definition.addPropertyValue("serviceName", annotationAttributes.get("serviceName"));
        definition.addPropertyValue("protocol", annotationAttributes.get("protocol"));
        definition.addPropertyValue("compress", annotationAttributes.get("compress"));
        definition.addPropertyValue("timeout", annotationAttributes.get("timeout"));
        try {
            definition.addPropertyValue("interfaceName", Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
