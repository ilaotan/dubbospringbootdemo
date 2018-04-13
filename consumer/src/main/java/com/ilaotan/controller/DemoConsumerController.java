
package com.ilaotan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.ilaotan.interfaces.IDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(description = "DemoConsumer", tags = {"DemoConsumer"})
public class DemoConsumerController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Reference(
            version = "1.0.0"
            , check = false
            , retries = 0
    )
    private IDemoService demoService;



    @GetMapping("/sayHello")
    @ApiOperation(value = "helloworld")
    public String sayHello(@RequestParam String name) {
        return demoService.sayHello(name);
    }


    @GetMapping("/setAttachment")
    @ApiOperation(value = "setAttachment")
    public String setAttachment(@RequestParam String name) {

        RpcContext.getContext().setAttachment("flowId", "snowflakeId" + System.currentTimeMillis());

        return demoService.sayHello2(name);
    }


}
