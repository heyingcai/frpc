package com.jibug.frpc.boot.registar;

import java.io.Serializable;

/**
 * @author heyingcai
 */
public class RpcClientDelegate extends RpcProxyFactoryBean implements Serializable {

    private static final long serialVersionUID = -6152821513259137910L;

    private String host;

    private String protocol;

    private String compressType;

    private int timeout;

    private int version;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCompressType() {
        return compressType;
    }

    public void setCompressType(String compressType) {
        this.compressType = compressType;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
