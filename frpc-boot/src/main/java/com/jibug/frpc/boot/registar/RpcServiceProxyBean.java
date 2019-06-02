package com.jibug.frpc.boot.registar;

import com.jibug.frpc.common.annotation.RpcService;
import com.jibug.frpc.common.config.RegistryConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author heyingcai
 */
public class RpcServiceProxyBean implements Serializable, ApplicationContextAware {

    private static final long serialVersionUID = 3533978485366285316L;

    public static final String BEAN_NAME = "rpcServiceProxyBean";

    private ApplicationContext applicationContext;

    private RegistryConfig registryConfig;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void start() {
        init();
    }

    public void init() {
        String[] beanNamesForAnnotation = applicationContext.getBeanNamesForAnnotation(RpcService.class);
        for (String beanName : beanNamesForAnnotation) {
            Object bean = applicationContext.getBean(beanName);

            Method[] methods = bean.getClass().getMethods();

            for (Method method : methods) {

            }
        }
    }

    public RegistryConfig getRegistryConfig() {
        return registryConfig;
    }

    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }
}
