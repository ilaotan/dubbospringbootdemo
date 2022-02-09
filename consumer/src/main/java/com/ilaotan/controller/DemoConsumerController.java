
package com.ilaotan.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ilaotan.interfaces.IDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.rpc.RpcContext;


@RestController
@RequestMapping("demo")
@Api(description = "DemoConsumer", tags = {"DemoConsumer"})
public class DemoConsumerController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @DubboReference(
            version = "1.0.0"
            , check = false
            , retries = 0
            , timeout = 5000
    )
    private IDemoService demoService;



    @GetMapping("/sayHello")
    @ApiOperation(value = "helloworld")
    public String sayHello(@RequestParam String name) {
        try {

            return demoService.sayHello(name);
        }catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }


    @GetMapping("/setAttachment")
    @ApiOperation(value = "setAttachment")
    public String setAttachment(@RequestParam String name) throws ExecutionException, InterruptedException {

        RpcContext.getContext().setAttachment("flowId", "snowflakeId" + System.currentTimeMillis());

        //使用异步姿势调用

        logger.debug("setAttachment  start");

        demoService.sayHello2(name);
        Future<String> cFuture = RpcContext.getContext().getFuture();


        String a = cFuture.get();

        return a;
    }


}
