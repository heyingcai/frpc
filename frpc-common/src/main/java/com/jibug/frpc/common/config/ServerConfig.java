package com.jibug.frpc.common.config;

/**
 * @author heyingcai
 */
public class ServerConfig {

    private int port;

    private String contextPath;

    private int threadPoolCore;

    private int threadPoolMax;

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
