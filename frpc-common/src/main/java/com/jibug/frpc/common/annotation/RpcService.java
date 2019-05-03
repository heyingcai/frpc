package com.jibug.frpc.common.annotation;

import com.jibug.frpc.common.cluster.HaStrategyType;
import com.jibug.frpc.common.cluster.LoadBalanceType;
import com.jibug.frpc.common.codec.compress.CompressEnum;
import com.jibug.frpc.common.codec.serialize.SerializeProtocolEnum;
import com.jibug.frpc.common.constant.ConfigConsts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RpcService {

    /**
     * 对应的服务application name
     *
     * @return
     */
    String serverName();

    /**
     * host 用于直连
     *
     * @return
     */
    String host() default "localhost";

    /**
     * port
     *
     * @return
     */
    int port() default ConfigConsts.DEFAULT_SERVER_PORT;

    /**
     * 服务提供者的bean name
     *
     * @return
     */
    String serviceName() default "";

    /**
     * 服务超时控制
     *
     * @return
     */
    int timeout() default ConfigConsts.DEFAULT_TIMEOUT;

    /**
     * 数据压缩类型
     *
     * @return
     */
    CompressEnum compress() default CompressEnum.NONE;

    /**
     * 序列化类型
     *
     * @return
     */
    SerializeProtocolEnum protocol() default SerializeProtocolEnum.JDK_SERIALIZE;

    /**
     * 容错机制
     *
     * @return
     */
    HaStrategyType haStrategyType() default HaStrategyType.FAIL_FAST;

    /**
     * 负载均衡策略
     *
     * @return
     */
    LoadBalanceType loadBalanceType() default LoadBalanceType.RANDOM;

}
