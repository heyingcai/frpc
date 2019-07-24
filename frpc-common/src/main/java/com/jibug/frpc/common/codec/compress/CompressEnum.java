package com.jibug.frpc.common.codec.compress;

/**
 * @author heyingcai
 */
public enum CompressEnum {
    NONE((byte) 0),
    SNAPPY((byte) (1 << 2)),
    ZLIB((byte) (1 << 3)),
    LZF((byte) (1 << 4)),
    LZ4((byte) (1 << 5));

    private byte value;

    CompressEnum(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
