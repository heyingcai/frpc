package com.jibug.frpc.common.cluster.registry;

/**
 * @author heyingcai
 */
public class ProviderInfo {

    private String url;

    private String host;

    private int port;

    public ProviderInfo() {
    }

    public ProviderInfo(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
