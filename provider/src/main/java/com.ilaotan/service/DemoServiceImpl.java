
package com.ilaotan.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.ilaotan.interfaces.IDemo2Service;
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


    @Reference(
            version = "1.0.0"
            , check = false
            , retries = 0
            , async = true
            //异步时,不配置timeout,使用sleep会报错,具体报错内容你可以注释调timeout后试试.
            , timeout = 60000
    )
    private IDemo2Service demo2Service;

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

    @Override
    public String testWithAsync() throws ExecutionException, InterruptedException {

        logger.debug("testWithAsync  -->  sayHelloWithSleep start");

        //同理使用异步姿势调用 sayHelloWithSleep
        // 这个线程会卡住6秒 超过主线程的4秒
        demo2Service.sayHelloWithSleep("张三年");

        Future<String> cFuture = RpcContext.getContext().getFuture();

        String res = cFuture.get();

        logger.debug("testWithAsync  -->  sayHelloWithSleep 拿到值 {}", res);

        return res;
    }

    @Override
    public String testWithSleep(long millis) {

        try {
            Thread.sleep(millis);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }


        return "休息" + millis + "后返回.......";
    }


}