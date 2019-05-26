package com.jibug.frpc.samples.provider.service;

import com.jibug.frpc.common.annotation.RpcService;
import com.jibug.frpc.samples.api.CalculateService;

/**
 * @author heyingcai
 */
@RpcService
public class CalculateServiceImpl implements CalculateService {

    @Override
    public int sum(int a, int b) {
        return a + b;
    }
}
