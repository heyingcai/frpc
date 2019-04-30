package com.jibug.frpc.samples;

import com.jibug.frpc.boot.annotation.EnableFrpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFrpc(basePackages = "com.jibug")
public class SamplesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SamplesApplication.class, args);
    }

}

