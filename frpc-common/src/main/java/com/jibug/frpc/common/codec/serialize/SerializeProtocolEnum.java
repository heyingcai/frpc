package com.jibug.frpc.common.codec.serialize;

/**
 * @author heyingcai
 */
public enum SerializeProtocolEnum {

    /**
     * jdk原生序列化
     */
    JDK_SERIALIZE("jdkserialize"),

    /**
     * hessian序列化
     */
    HESSIAN_SERIALIZE("hessian"),

    /**
     * kryo序列化
     */
    KRYO_SERIALIZE("kryo"),

    /**
     * protostuff序列化
     */
    PROTOSTUFF_SERIALIZE("protostuff"),

    /**
     * json序列化
     */
    JSON_SERIALIZE("json");

    private String serialize;

    SerializeProtocolEnum(String serialize) {
        this.serialize = serialize;
    }

    public String getSerialize() {
        return serialize;
    }
}
