
package com.ilaotan.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ilaotan.interfaces.IDemoService;
import org.apache.dubbo.rpc.RpcContext;


//@Service(interfaceClass = DemoService.class, version = "1.0.0")
@DubboService(
        version = "1.0.0"
)
public class DemoServiceImpl implements IDemoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public String sayHello(String name) {
        logger.warn("Hello, {}(from Spring Boot)", name);
        return "Hello, " + name + " (from Spring Boot)";
    }

    @Override
    public String sayHello2(String name) {

        String flowId = RpcContext.getContext().getAttachment("flowId");

        logger.warn("Hello, {}(from Spring Boot), your flowId is {}", name, flowId);
        return "Hello, " + name + " (from Spring Boot), your flowId is " + flowId;
    }








}
