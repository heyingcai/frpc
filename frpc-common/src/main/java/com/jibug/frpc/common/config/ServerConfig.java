package com.jibug.frpc.common.config;

import static com.jibug.frpc.common.config.RpcServerConfig.getIntValue;
import static com.jibug.frpc.common.config.RpcServerConfig.getStringValue;
import static com.jibug.frpc.common.constant.ConfigPropertiesKey.*;

/**
 * @author heyingcai
 */
public class ServerConfig {

    private int port = getIntValue(SERVER_PORT);

    private String contextPath = getStringValue(SERVER_CONTEXT_PATH);

    private int threadPoolCore = getIntValue(SERVER_THREAD_POOL_CORE);

    private int threadPoolMax = getIntValue(SERVER_THREAD_POOL_MAX);

    public ServerConfig(int port, String contextPath, int threadPoolCore, int threadPoolMax) {
        this.port = port;
        this.contextPath = contextPath;
        this.threadPoolCore = threadPoolCore;
        this.threadPoolMax = threadPoolMax;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public int getThreadPoolCore() {
        return threadPoolCore;
    }

    public void setThreadPoolCore(int threadPoolCore) {
        this.threadPoolCore = threadPoolCore;
    }

    public int getThreadPoolMax() {
        return threadPoolMax;
    }

    public void setThreadPoolMax(int threadPoolMax) {
        this.threadPoolMax = threadPoolMax;
    }
}
