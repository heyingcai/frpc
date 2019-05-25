package com.jibug.frpc.common.cluster.registry;

/**
 * @author heyingcai
 */
public enum RegistryProtocolEnum {

    /**
     * local注册中心
     */
    LOCAL("local", LocalRegistry.class),
    /**
     * zookeeper注册中心
     */
    ZOOKEEPER("zookeeper", ZookeeperRegistry.class),
    /**
     * consul注册中心
     */
    CONSUL("consul", ConsulRegistry.class),
    /**
     * eureka注册中心
     */
    EUREKA("eureka", EurekaRegistry.class),
    /**
     * redis注册中心
     */
    REDIS("redis", RedisRegistry.class);


    private String name;
    private Class<?> clazz;

    RegistryProtocolEnum(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public static Class<?> getClassTypeByName(String name) {
        RegistryProtocolEnum[] values = RegistryProtocolEnum.values();
        for (RegistryProtocolEnum protocolEnum : values) {
            if (protocolEnum.getName().equalsIgnoreCase(name)) {
                return protocolEnum.getClazz();
            }
        }
        return null;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
