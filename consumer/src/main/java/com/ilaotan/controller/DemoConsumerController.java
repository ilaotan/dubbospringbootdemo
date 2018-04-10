
package com.ilaotan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ilaotan.interfaces.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(description = "DemoConsumer", tags = {"DemoConsumer"})
public class DemoConsumerController {

    @Reference(version = "1.0.0", check = false)
    private DemoService demoService;

    @GetMapping("/sayHello")
    @ApiOperation(value = "helloworld")
    public String sayHello(@RequestParam String name) {
        return demoService.sayHello(name);
    }

}
