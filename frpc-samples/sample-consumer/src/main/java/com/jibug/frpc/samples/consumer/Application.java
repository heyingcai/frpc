package com.jibug.frpc.samples.consumer;

import com.jibug.frpc.boot.annotation.EnableFrpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author heyingcai
 */
@SpringBootApplication
@EnableFrpc(basePackages = "com.jibug.frpc")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
