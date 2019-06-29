package com.jibug.frpc.common.cluster.registry;

import com.jibug.frpc.common.config.ConsumerConfig;
import com.jibug.frpc.common.config.ProviderConfig;
import com.jibug.frpc.common.config.RegistryConfig;
import com.jibug.frpc.common.config.ServerConfig;
import com.jibug.frpc.common.exception.FrpRuntimeException;
import com.jibug.frpc.common.util.NetUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author heyingcai
 */
public class ZookeeperRegistry extends Registry implements ApplicationContextAware {

    private CuratorFramework zkClient;
    private ApplicationContext applicationContext;

    protected ZookeeperRegistry(RegistryConfig registryConfig) {
        super(registryConfig);
    }

    private ConcurrentMap<ProviderConfig, List<String>> providerUrls = new ConcurrentHashMap<>();


    @Override
    public synchronized void init() {
        if (zkClient != null) {
            return;
        }
        String address = registryConfig.getAddress();
        if (StringUtils.isBlank(address)) {
            throw new FrpRuntimeException("The registry address is empty.");
        }
        zkClient = CuratorFrameworkFactory.builder()
                .connectString(address)
                .canBeReadOnly(false)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .defaultData(null)
                .build();
    }

    @Override
    public synchronized boolean start() {
        init();
        if (zkClient == null) {
            return false;
        }
        if (zkClient.getState() == CuratorFrameworkState.STARTED) {
            return true;
        }
        try {
            zkClient.start();
        } catch (Exception e) {
            throw new FrpRuntimeException("zookeeper client start failed.", e);
        }
        return zkClient.getState() == CuratorFrameworkState.STARTED;
    }

    @Override
    public void register(ProviderConfig config) {

    }

    @Override
    public void unRegister(ProviderConfig config) {

    }


    @Override
    public List<ProviderInfo> subscribe(ConsumerConfig config) {
        return null;
    }

    @Override
    public void unSubscribe(ConsumerConfig config) {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected void registerProviderUrl(ProviderConfig config) {
        try {
            ServerConfig serverConfig = config.getServerConfig();
            StringBuilder urlBuilder = new StringBuilder(200);
            String host = serverConfig.getHost();
            if (NetUtils.isInvalidLocalHost(host)) {
                host = NetUtils.getLocalAddress().getHostName();
            }

        } catch (Exception e) {

        }

    }
}
