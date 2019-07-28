package com.jibug.frpc.common.codec.compress;

/**
 * @author heyingcai
 */
public enum CompressEnum {
    NONE((byte) 0, null),
    SNAPPY((byte) (1 << 2), SnappyCompress.class),
    LZ0((byte) (1 << 3), LzoCompress.class),
    LZ4((byte) (1 << 4), Lz4Compress.class);

    private byte value;
    private Class<?> clazz;

    public static Class<?> getCompressClass(byte value) {
        CompressEnum[] values = values();
        for (CompressEnum c : values) {
            if (c.value == value) {
                return c.clazz;
            }
        }
        return null;
    }

    CompressEnum(byte value, Class<?> clazz) {
        this.value = value;
        this.clazz = clazz;
    }

    CompressEnum(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
