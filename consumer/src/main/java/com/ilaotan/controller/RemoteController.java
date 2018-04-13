package com.ilaotan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.ilaotan.util.ReferenceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/4/13 13:32
 */

@RestController("remote")
@Api(description = "RemoteController", tags = {"RemoteController"})
public class RemoteController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationContext applicationContext;


    @GetMapping("/remoteInvoke")
    @ApiOperation(value = "remoteInvoke", notes = "测试: 不依赖Interface,直接调用dubbo接口")
    public String remoteInvoke(){

        String interfaceName = "com.ilaotan.interfaces.IDemoService";
        String id = "demoServiceRemote";

        Object refer = ReferenceUtil.refer(applicationContext, id, interfaceName);

        Class[] paramTypes = new Class[1];
        paramTypes[0] = String.class;
        String[] params = new String[]{"直接远程调用试试...."};
        Object res = null;
        try {
            res = refer.getClass().getDeclaredMethod("sayHello", paramTypes).invoke(refer, params);

            logger.debug("远程调用.... 返回值是: {}" ,res);
        }catch (Exception e){
            res = "执行失败:" + e.getMessage();
            logger.error(e.getMessage(),e);
        }
        return JSON.toJSONString(res);
    }

}
