package com.jibug.frpc.samples.consumer.controller;

import com.jibug.frpc.samples.api.CalculateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author heyingcai
 */
@RestController
public class TestController {

    @Resource
    private CalculateService calculateService;

    @RequestMapping(value = "/sum")
    public void sum() {
        calculateService.sum(5, 4);
    }

}
