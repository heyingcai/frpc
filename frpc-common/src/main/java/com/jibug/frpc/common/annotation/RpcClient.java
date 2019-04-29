package com.jibug.frpc.common.annotation;

import com.jibug.frpc.common.codec.compress.CompressEnum;
import com.jibug.frpc.common.codec.serialize.SerializeProtocolEnum;
import com.jibug.frpc.common.constant.ConfigConsts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RpcClient {

    String serverName();

    String host() default "localhost";

    int port() default ConfigConsts.DEFAULT_SERVER_PORT;

    String serviceName() default "";

    int timeout() default ConfigConsts.DEFAULT_TIMEOUT;

    CompressEnum compress() default CompressEnum.NONE;

    SerializeProtocolEnum protocol() default SerializeProtocolEnum.JDK_SERIALIZE;

}
