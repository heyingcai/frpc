package com.jibug.frpc.boot.registar;

import com.jibug.frpc.common.codec.compress.CompressEnum;
import com.jibug.frpc.common.codec.serialize.SerializeProtocolEnum;

import java.io.Serializable;

/**
 * @author heyingcai
 */
public class RpcServiceDelegate extends RpcProxyFactoryBean implements Serializable {

    private static final long serialVersionUID = -6152821513259137910L;

    private String serverName;

    private String host;

    private int port;

    private String serviceName;

    private SerializeProtocolEnum protocol;

    private CompressEnum compress;

    private int timeout;

    private int version;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public SerializeProtocolEnum getProtocol() {
        return protocol;
    }

    public void setProtocol(SerializeProtocolEnum protocol) {
        this.protocol = protocol;
    }

    public CompressEnum getCompress() {
        return compress;
    }

    public void setCompress(CompressEnum compress) {
        this.compress = compress;
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
