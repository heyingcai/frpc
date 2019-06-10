# frpc

[![Build Status](https://travis-ci.com/heyingcai/frpc.svg?branch=master)](https://travis-ci.org/heyingcai/frpc)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/dempeZheng/forest/blob/master/LICENSE)


>frpc是一款基于Netty的轻量级高性能分布式RPC服务框架。

## 功能介绍
* 支持Springboot主流框架，使用约定大于配置原则，便捷高效地开发分布式应用。
* 支持主流常用的序列化方式，如：fastjson，hession，kryo。
* 支持数据包压缩方式，如：Snappy,Lz4等。
* 支持负载均衡，服务可用性配置。

## 快速开始
### Server端
启动类配置如下：
```
@SpringBootApplication
//启用Frpc框架，并指定扫描路径
@EnableFrpc(basePackages = "com.jibug")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run( Application.class, args );
    }
}


```

写一个服务，接口如下：
```
@RpcInterface(serverName = "cal-server", serviceName = "calculateService", host = "127.0.0.1", port = 8000)
public interface CalculateService {

    int sum(int a, int b);
}

```
同时编写对应的实现类：
使用@RpcService注解标注在实现类上
```
@RpcService
public class CalculateServiceImpl implements CalculateService {

    @Override
    public int sum(int a, int b) {
        return a + b;
    }
}
```

application.yml配置:
```
frpc:
  registry:
    address: localhost:2181
    protocol: zookeeper
  server:
    port: 123456

```

### Client端
启动类配置如下：
```
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
```
    @RpcReference
    private CalculateService calculateService;
```




