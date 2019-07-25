package com.jibug.frpc.common.model;

import com.jibug.frpc.common.util.SnowflakeIdWorker;

import java.io.Serializable;

/**
 * @author heyingcai
 */
public class FrpcRequestHeader implements Serializable {

    private static final long serialVersionUID = -8864145539597595936L;

    private byte magic;

    private byte version;

    private byte compress;

    private byte type;

    private byte codec;

    private String requestId;

    private Integer size;

    public FrpcRequestHeader() {
        this.requestId = String.valueOf(SnowflakeIdWorker.getInstance().nextId());
    }

    public FrpcRequestHeader(byte magic, byte version, byte compress, byte codec, byte type) {
        this.magic = magic;
        this.version = version;
        this.compress = compress;
        this.codec = codec;
        this.type = type;
        this.requestId = String.valueOf(SnowflakeIdWorker.getInstance().nextId());
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public byte getMagic() {
        return magic;
    }

    public void setMagic(byte magic) {
        this.magic = magic;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getCompress() {
        return compress;
    }

    public void setCompress(byte compress) {
        this.compress = compress;
    }

    public byte getCodec() {
        return codec;
    }

    public void setCodec(byte codec) {
        this.codec = codec;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
