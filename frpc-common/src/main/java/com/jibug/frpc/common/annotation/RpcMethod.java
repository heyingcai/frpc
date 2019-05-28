package com.jibug.frpc.common.annotation;

import com.jibug.frpc.common.cluster.enums.RequestType;
import com.jibug.frpc.common.codec.compress.CompressEnum;
import com.jibug.frpc.common.codec.serialize.SerializeProtocolEnum;
import com.jibug.frpc.common.constant.ConfigConsts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author heyingcai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RpcMethod {

    String methodName() default "";

    int timeout() default ConfigConsts.DEFAULT_TIMEOUT;

    CompressEnum compress() default CompressEnum.NONE;

    SerializeProtocolEnum serialze() default SerializeProtocolEnum.JDK_SERIALIZE;

    RequestType callType() default RequestType.SYNC;

}
