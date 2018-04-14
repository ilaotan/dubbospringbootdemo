package com.ilaotan.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.ilaotan.interfaces.IDemoOtherAsyncService;
import com.ilaotan.interfaces.IDemoAsyncService;
import com.ilaotan.interfaces.IDemoOtherService;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/4/14 11:01
 */
@Service(
        version = "1.0.0"
        , application = "${dubbo.application.id}"
        , protocol = "${dubbo.protocol.id}"
        , registry = "${dubbo.registry.id}"
)
public class DemoAsyncService implements IDemoAsyncService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Reference(
            version = "1.0.0"
            , check = false
            , retries = 0
            , async = true
            //异步时,不配置timeout,使用sleep会报错,具体报错内容你可以注释调timeout后试试.
            , timeout = 60000
    )
    private IDemoOtherAsyncService demoOtherAsyncService;

    @Reference(
            version = "1.0.0"
            , check = false
            , retries = 0
            //异步时,不配置timeout,使用sleep会报错,具体报错内容你可以注释调timeout后试试.
            , timeout = 60000
    )
    private IDemoOtherService demoOtherService;


    @Override
    public String testWithAsync() throws ExecutionException, InterruptedException {

        logger.debug("testWithAsync  -->  sayHelloWithSleep start");

        //同理使用异步姿势调用 sayHelloWithSleep
        // 这个线程会卡住6秒 超过主线程的4秒
        demoOtherAsyncService.sayHelloWithSleep("张三年");

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


    @Override
    public String testWithNoAsync() {

        String res = demoOtherService.sayHelloWithSleep("张三年");

        logger.debug("testWithNoAsync  -->  sayHelloWithSleep 拿到值 {}", res);
        return res;
    }

}
