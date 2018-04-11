
package com.ilaotan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.ilaotan.interfaces.IDemoService;


//@Service(interfaceClass = DemoService.class, version = "1.0.0")
@Service(
        version = "1.0.0"
        , application = "${dubbo.application.id}"
        , protocol = "${dubbo.protocol.id}"
        , registry = "${dubbo.registry.id}"
)
public class DemoServiceImpl implements IDemoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String sayHello(String name) {
        logger.warn("Hello, {}(from Spring Boot)", name);
        return "Hello, " + name + " (from Spring Boot)";
    }

}