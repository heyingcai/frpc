package com.jibug.frpc.boot.annotation;

import com.jibug.frpc.boot.registar.FrpcRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({FrpcRegistrar.class})
public @interface EnableFrpc {

    String[] basePackages() default {"com.jibug"};

}
