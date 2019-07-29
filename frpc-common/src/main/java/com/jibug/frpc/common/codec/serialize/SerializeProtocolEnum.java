package com.jibug.frpc.common.codec.serialize;

import com.jibug.frpc.common.codec.serialize.hessian.HessianSerialize;
import com.jibug.frpc.common.codec.serialize.jdk.JdkSerialize;
import com.jibug.frpc.common.codec.serialize.json.JsonSerialize;
import com.jibug.frpc.common.codec.serialize.kryo.KryoSerialize;

/**
 * @author heyingcai
 */
public enum SerializeProtocolEnum {

    /**
     * jdk原生序列化
     */
    JDK_SERIALIZE((byte) 0, JdkSerialize.class),

    /**
     * hessian序列化
     */
    HESSIAN_SERIALIZE((byte) (1 << 2), HessianSerialize.class),

    /**
     * kryo序列化
     */
    KRYO_SERIALIZE((byte) (1 << 3), KryoSerialize.class),

    /**
     * json序列化
     */
    JSON_SERIALIZE((byte) (1 << 4), JsonSerialize.class);

    private byte value;

    private Class<?> clazz;

    SerializeProtocolEnum(byte value, Class<?> clazz) {
        this.value = value;
        this.clazz = clazz;
    }

    public static Class<?> getSerialize(byte value) {
        SerializeProtocolEnum[] values = values();
        for (SerializeProtocolEnum s : values) {
            if (s.value == value) {
                return s.clazz;
            }
        }
        return null;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
