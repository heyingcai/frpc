package com.jibug.frpc.boot.registar;

import com.jibug.frpc.common.cluster.enums.HaStrategyType;
import com.jibug.frpc.common.cluster.enums.LoadBalanceType;
import com.jibug.frpc.common.codec.compress.CompressEnum;
import com.jibug.frpc.common.codec.serialize.SerializeProtocolEnum;

import java.io.Serializable;

/**
 * @author heyingcai
 */
public class RpcServiceProxyBean extends RpcProxyFactoryBean implements Serializable {

    private static final long serialVersionUID = 3533978485366285316L;

    private int port;

}
