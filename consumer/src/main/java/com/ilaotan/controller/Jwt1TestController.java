package com.ilaotan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ilaotan.jwt.SonliJwt;
import io.swagger.annotations.Api;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/5/31 14:18
 */

@RestController("jwt1")
@Api(description = "JwtTestController", tags = {"JwtTestController"})
public class Jwt1TestController {


    @GetMapping("/jwt1Test1")
    @SonliJwt
    public String jwt1Test1(){


        return "success";
    }

}
