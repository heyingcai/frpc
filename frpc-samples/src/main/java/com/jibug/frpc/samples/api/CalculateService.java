package com.jibug.frpc.samples.api;

import com.jibug.frpc.common.annotation.RpcService;

/**
 * @author heyingcai
 */
@RpcService(serverName = "cal-server",serviceName = "calculateService")
public interface CalculateService {

    int sum(int a, int b);

}
