package com.jibug.frpc.boot.registar;

import com.jibug.frpc.common.annotation.RpcInterface;
import com.jibug.frpc.common.annotation.RpcMethod;
import com.jibug.frpc.common.annotation.RpcService;
import com.jibug.frpc.common.cluster.registry.Registry;
import com.jibug.frpc.common.config.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author heyingcai
 */
public class RpcServiceProxyBean implements Serializable, InitializingBean, ApplicationContextAware {

    private static final long serialVersionUID = 3533978485366285316L;

    public static final String BEAN_NAME = "rpcServiceProxyBean";

    private ApplicationContext applicationContext;

    private RegistryConfig registryConfig;

    private Registry registry;

    private ServerConfig serverConfig;

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

            Class<?>[] interfaces = bean.getClass().getInterfaces();
            for (Class<?> interfazz : interfaces) {
                RpcInterface rpcInterface = interfazz.getAnnotation(RpcInterface.class);
                if (rpcInterface == null) {
                    continue;
                }

                ServiceConfig serviceConfig = new ServiceConfig();
                serviceConfig.setCompressEnum(rpcInterface.compress());
                serviceConfig.setHaStrategyType(rpcInterface.haStrategyType());
                serviceConfig.setLoadBalanceType(rpcInterface.loadBalanceType());
                serviceConfig.setHost(rpcInterface.host());
                serviceConfig.setPort(rpcInterface.port());
                serviceConfig.setProtocolEnum(rpcInterface.protocol());
                serviceConfig.setServerName(rpcInterface.serverName());
                serviceConfig.setServiceName(rpcInterface.serviceName());
                serviceConfig.setTimeout(rpcInterface.timeout());

                for (Method method : interfazz.getMethods()) {
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

                ProviderConfig providerConfig = new ProviderConfig();
                providerConfig.setInterfaceId(StringUtils.isBlank(rpcInterface.serviceName()) ? interfazz.getSimpleName() : rpcInterface.serviceName());
                providerConfig.setServiceConfig(serviceConfig);
                providerConfig.setServerConfig(getServerConfig());
                registry.register(providerConfig);
            }

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

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.registry = (Registry) applicationContext.getBean(FrpcRegistrar.REGISTRY_CENTER);
    }
}
