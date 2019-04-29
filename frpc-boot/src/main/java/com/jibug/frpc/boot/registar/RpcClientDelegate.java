package com.jibug.frpc.boot.registar;

import com.jibug.frpc.common.codec.compress.CompressEnum;
import com.jibug.frpc.common.codec.serialize.SerializeProtocolEnum;

import java.io.Serializable;

/**
 * @author heyingcai
 */
public class RpcClientDelegate extends RpcProxyFactoryBean implements Serializable {

    private static final long serialVersionUID = -6152821513259137910L;

    private String host;

    private SerializeProtocolEnum protocol;

    private CompressEnum compress;

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
