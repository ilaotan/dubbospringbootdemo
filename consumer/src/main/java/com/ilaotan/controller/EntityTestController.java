package com.ilaotan.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ilaotan.interfaces.IEntityTestService;
import com.ilaotan.model.TestModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/5/15 11:15
 */
@RestController
@Api(description = "EntityTest", tags = {"EntityTest"})
public class EntityTestController {

    @Reference(
            version = "1.0.0"
            , check = false
            , retries = 0
            , timeout = 60000
    )
    private IEntityTestService entityTestService;


    @GetMapping("/entityTest")
    @ApiOperation(value = "entityTest")
    public String entityTest(@RequestParam String name) {

        TestModel testModel = new TestModel();
        testModel.setName("张三");
        testModel.setNum3(3);

        System.out.println(JSON.toJSONString(testModel));

        return entityTestService.test(testModel);
    }

}
