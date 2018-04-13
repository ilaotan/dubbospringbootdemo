
package com.ilaotan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ilaotan.interfaces.ICacheService;
import com.ilaotan.interfaces.IDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController("cache")
@Api(description = "CacheController", tags = {"CacheController"})
public class CacheController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Reference(
            version = "1.0.0"
            , check = false
            , retries = 0
    )
    private ICacheService cacheService;



    @GetMapping("/getNews")
    @ApiOperation(value = "getNews")
    public String getNews(@RequestParam String type) {
        return cacheService.getNews(type);
    }

}
