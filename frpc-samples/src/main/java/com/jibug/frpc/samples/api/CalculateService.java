package com.jibug.frpc.samples.api;

import com.jibug.frpc.common.annotation.RpcService;

/**
 * @author heyingcai
 */
@RpcService(serverName = "cal-server", serviceName = "calculateService", host = "127.0.0.1", port = 8000)
public interface CalculateService {

    int sum(int a, int b);

}
