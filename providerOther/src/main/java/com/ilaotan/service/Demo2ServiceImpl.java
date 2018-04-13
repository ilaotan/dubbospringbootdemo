package com.ilaotan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.ilaotan.interfaces.IDemo2Service;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/4/13 15:27
 */
@Service(
        version = "1.0.0"
        , application = "${dubbo.application.id}"
        , protocol = "${dubbo.protocol.id}"
        , registry = "${dubbo.registry.id}"
        , group = "aaa"
)
public class Demo2ServiceImpl implements IDemo2Service {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public String sayHelloWithSleep(String name) {
        try {
            Thread.sleep(6000);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        logger.warn("Hello, {}(from Spring Boot sayHelloWithSleep)", name);
        return "Hello, " + name + " (from Spring Boot sayHelloWithSleep)";
    }


}
