package com.jibug.frpc.common.config;

import com.jibug.frpc.common.cluster.enums.HaStrategyType;
import com.jibug.frpc.common.cluster.enums.LoadBalanceType;

/**
 * @author heyingcai
 */
public class ConsumerConfig {

    private String protocol;

    private String directAddr;

    private int connectTimeout;

    private HaStrategyType  haStrategyType;

    private LoadBalanceType loadBalanceType;

}
