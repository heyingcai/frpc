package com.jibug.frpc.common.cluster.registry;

/**
 * @author heyingcai
 */
public enum RegistryProtocolEnum {

    LOCAL("local", LocalRegistry.class),
    ZOOKEEPER("zookeeper", ZookeeperRegistry.class),
    CONSUL("consul", ConsulRegistry.class),
    EUREKA("eureka", EurekaRegistry.class);


    private String name;
    private Class<?> clazz;

    RegistryProtocolEnum(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
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
