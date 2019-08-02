package com.jibug.frpc.common.cluster.registry;

import com.jibug.frpc.common.cluster.registry.utils.RegistryHelper;
import com.jibug.frpc.common.config.ConsumerConfig;
import com.jibug.frpc.common.config.ProviderConfig;
import com.jibug.frpc.common.config.RegistryConfig;
import com.jibug.frpc.common.exception.FrpcRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.jibug.frpc.common.cluster.registry.utils.RegistryHelper.convertUrlToProvider;
import static com.jibug.frpc.common.cluster.registry.utils.RegistryHelper.createProviderPath;

/**
 * @author heyingcai
 */
public class ZookeeperRegistry extends Registry implements ApplicationContextAware {

    private CuratorFramework zkClient;
    private ApplicationContext applicationContext;

    protected ZookeeperRegistry(RegistryConfig registryConfig) {
        super(registryConfig);
    }

    private ConcurrentMap<ProviderConfig, String> providerUrls = new ConcurrentHashMap<>();

    private static final ConcurrentMap<String, PathChildrenCache> INTERFACE_PROVIDER_CACHE = new ConcurrentHashMap<>();


    @Override
    public synchronized void init() {
        if (zkClient != null) {
            return;
        }
        String address = registryConfig.getAddress();
        if (StringUtils.isBlank(address)) {
            throw new FrpcRuntimeException("The registry address is empty.");
        }
        zkClient = CuratorFrameworkFactory.builder()
                .connectString(address)
                .canBeReadOnly(false)
                .retryPolicy(new ExponentialBackoffRetry(10000, 3))
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
            throw new FrpcRuntimeException("zookeeper client start failed.", e);
        }
        return zkClient.getState() == CuratorFrameworkState.STARTED;
    }

    @Override
    public void register(ProviderConfig config) {
        try {
            String url = providerUrls.get(config);
            if (url == null) {
                url = RegistryHelper.convertConfigToUrl(config);
                providerUrls.putIfAbsent(config, url);
            }
            if (StringUtils.isNotBlank(url)) {
                String providerPath = createProviderPath(config.getInterfaceId());
                url = URLEncoder.encode(url, "UTF-8");
                String providerUrl = providerPath + "/" + url;
                try {
                    getAndCheckZkClient().create().creatingParentContainersIfNeeded()
                            .withMode(CreateMode.EPHEMERAL)
                            .forPath(providerUrl, ProviderStatusEnum.ONLINE.getStatus());
                } catch (KeeperException.NodeExistsException nodeExistsException) {
                }
            }
        } catch (Exception e) {
            throw new FrpcRuntimeException("Register provider config to Zookeeper failed", e);
        }
    }

    @Override
    public void unRegister(ProviderConfig config) {

    }


    @Override
    public List<ProviderInfo> subscribe(ConsumerConfig config) {
        try {
            String providerPath = createProviderPath(config.getInterfaceId());
            PathChildrenCache pathChildrenCache = INTERFACE_PROVIDER_CACHE.get(config.getInterfaceId());
            if (pathChildrenCache == null) {
                pathChildrenCache = new PathChildrenCache(zkClient, providerPath, true);
                pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
                INTERFACE_PROVIDER_CACHE.put(config.getInterfaceId(), pathChildrenCache);
            }
            return convertUrlToProvider(providerPath, pathChildrenCache.getCurrentData());
        } catch (Exception e) {
            throw new FrpcRuntimeException("Failed to subscribe providerInfo", e);
        }
    }

    @Override
    public void unSubscribe(ConsumerConfig config) {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private CuratorFramework getAndCheckZkClient() {
        if (zkClient == null || zkClient.getState() != CuratorFrameworkState.STARTED) {
            throw new FrpcRuntimeException("Zookeeper client is not available");
        }
        return zkClient;
    }

    enum ProviderStatusEnum {
        OFFLINE(new byte[]{0}),
        ONLINE(new byte[]{1});

        private byte[] status;

        ProviderStatusEnum(byte[] status) {
            this.status = status;
        }

        public byte[] getStatus() {
            return status;
        }

        public void setStatus(byte[] status) {
            this.status = status;
        }
    }
}
