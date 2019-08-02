package com.jibug.frpc.samples.consumer.controller;

import com.jibug.frpc.common.annotation.RpcReference;
import com.jibug.frpc.samples.api.CalculateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heyingcai
 */
@RestController
public class TestController {

    @RpcReference
    private CalculateService calculateService;

    @RequestMapping(value = "/sum")
    public Integer sum() {
        int sum = calculateService.sum(5, 4);
        return sum;
    }

}
