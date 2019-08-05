# frpc

[![Build Status](https://api.travis-ci.org/heyingcai/frpc.svg?branch=master)](https://travis-ci.org/heyingcai/frpc)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/dempeZheng/forest/blob/master/LICENSE)


>frpc是一款基于Netty的轻量级高性能分布式RPC服务框架。

## 功能介绍
* 支持Springboot主流框架，使用约定大于配置原则，便捷高效地开发分布式应用。
* 支持主流注册中心，如:Zookeeper,Consul,Eureka。
* 支持主流常用的序列化方式，如：fastjson，hession，kryo。
* 支持数据包压缩方式，如：Snappy,Lz4等。
* 支持负载均衡，服务可用性配置。

## 快速开始

写一个api服务，接口如下：
```java
//指定服务提供者的service名称（spring ioc bean name）
@RpcInterface(serviceName = "calculateServiceImpl")
public interface CalculateService {

    int sum(int a, int b);
}

```

### Server端
application.yml配置:
```yaml
frpc:
  registry:
    address: localhost:2181
    protocol: zookeeper
  server:
    port: 123456

```

启动类配置如下：
```java
@SpringBootApplication
//启用Frpc框架，并指定扫描路径
@EnableFrpc(basePackages = "com.jibug")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run( Application.class, args );
    }
}


```
同时编写对应的实现类：
使用@RpcService注解标注在实现类上
```java
@RpcService
public class CalculateServiceImpl implements CalculateService {

    @Override
    public int sum(int a, int b) {
        return a + b;
    }
}
```

### Client端
application.yml配置:
```yaml
frpc:
  registry:
    address: localhost:2181 
    protocol: zookeeper
  server:
    port: 123456

```

启动类配置如下：
```java
@SpringBootApplication
//启用Frpc框架，并指定扫描路径
@EnableFrpc(basePackages = "com.jibug")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run( Application.class, args );
    }
}


```
在需要引用服务的地方使用@RpcReference注解进行注入
```java
    @RpcReference
    private CalculateService calculateService;
```




