package com.jibug.frpc.boot.registar;

import com.jibug.frpc.common.annotation.RpcService;
import com.jibug.frpc.common.cluster.registry.Registry;
import com.jibug.frpc.common.config.ProviderConfig;
import com.jibug.frpc.common.config.RegistryConfig;
import com.jibug.frpc.common.config.ServerConfig;
import com.jibug.frpc.net.AbstractServer;
import com.jibug.frpc.net.NettyServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;

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

    private AbstractServer server;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void start() throws InterruptedException {
        init();
    }

    public void init() throws InterruptedException {
        String[] beanNamesForAnnotation = applicationContext.getBeanNamesForAnnotation(RpcService.class);
        for (String beanName : beanNamesForAnnotation) {
            Object bean = applicationContext.getBean(beanName);

            String serviceName = bean.getClass().getAnnotation(RpcService.class).name();
            ProviderConfig providerConfig = new ProviderConfig();
            providerConfig.setInterfaceId(StringUtils.isNotBlank(serviceName) ? serviceName : bean.getClass().getSimpleName());
            providerConfig.setServerConfig(serverConfig);

            registry.register(providerConfig);
        }
        if (beanNamesForAnnotation.length > 0) {
            server = new NettyServer(serverConfig);
            server.doInit();
            server.doStart();
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
