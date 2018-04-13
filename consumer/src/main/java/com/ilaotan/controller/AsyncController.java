package com.ilaotan.controller;

import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ilaotan.interfaces.ICacheService;
import com.ilaotan.interfaces.IDemoService;
import io.swagger.annotations.Api;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/4/11 18:01
 */

@RestController("async")
@Api(description = "AsyncController", tags = {"AsyncController"})
public class AsyncController {

    @Reference(
            version = "1.0.0"
            , check = false
            , retries = 0
    )
    private ICacheService cacheService;

    @Reference(
            version = "1.0.0"
            , check = false
            , retries = 0
    )
    private IDemoService demoService;





}
