package com.jibug.frpc.samples.api;

import com.jibug.frpc.common.annotation.RpcInterface;
import com.jibug.frpc.common.codec.compress.CompressEnum;
import com.jibug.frpc.common.codec.serialize.SerializeProtocolEnum;

/**
 * @author heyingcai
 */
@RpcInterface(serverName = "cal-server", serviceName = "calculateServiceImpl", protocol = SerializeProtocolEnum.HESSIAN_SERIALIZE,compress = CompressEnum.SNAPPY)
public interface CalculateService {

    int sum(int a, int b);

}
