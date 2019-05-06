package com.jibug.frpc.common.annotation;

import com.jibug.frpc.common.constant.ConfigConsts;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author heyingcai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface RpcService {

    int port() default ConfigConsts.DEFAULT_SERVER_PORT;

}
