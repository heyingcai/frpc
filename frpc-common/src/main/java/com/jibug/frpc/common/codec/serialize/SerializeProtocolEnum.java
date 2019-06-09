package com.jibug.frpc.common.codec.serialize;

import com.jibug.frpc.common.codec.serialize.hessian.HessianSerialize;
import com.jibug.frpc.common.codec.serialize.json.JsonSerialize;
import com.jibug.frpc.common.codec.serialize.kryo.KryoSerialize;

/**
 * @author heyingcai
 */
public enum SerializeProtocolEnum {

    /**
     * jdk原生序列化
     */
    JDK_SERIALIZE("jdkserialize", null),

    /**
     * hessian序列化
     */
    HESSIAN_SERIALIZE("hessian", HessianSerialize.class),

    /**
     * kryo序列化
     */
    KRYO_SERIALIZE("kryo", KryoSerialize.class),

    /**
     * json序列化
     */
    JSON_SERIALIZE("json", JsonSerialize.class);

    private String serialize;

    private Class<?> clazz;

    SerializeProtocolEnum(String serialize, Class<?> clazz) {
        this.serialize = serialize;
        this.clazz = clazz;
    }

    public void setSerialize(String serialize) {
        this.serialize = serialize;
    }

    public String getSerialize() {
        return serialize;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
