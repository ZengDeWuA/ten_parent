package com.tensquare.qa.client.impl;

import com.tensquare.qa.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

@Component
public class LabelClientImpl implements LabelClient {

    @Override
    public Result findById(String labelId) {
        System.out.println("熔断器");
        return new Result(false, StatusCode.ERROR, "熔断器启动了");
    }
}
