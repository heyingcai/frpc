package com.jibug.frpc.boot.registar;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        }else {
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
        try {
            String basePackage = Class.forName(annotationMetadata.getClassName()).getPackage().getName();
            System.out.println(basePackage);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
