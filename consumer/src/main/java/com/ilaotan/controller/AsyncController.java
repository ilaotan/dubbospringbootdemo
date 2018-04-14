package com.ilaotan.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.ilaotan.interfaces.IDemoAsyncService;
import com.ilaotan.interfaces.IDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/4/11 18:01
 */

@RestController("async")
@Api(description = "AsyncController", tags = {"AsyncController"})
public class AsyncController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Reference(
            version = "1.0.0"
            , check = false
            , retries = 0
            , async = true
            // 异步时,不加timeout会报错.
            , timeout = 60000
    )
    private IDemoAsyncService demoAsyncService;

    @Reference(
            version = "1.0.0"
            , check = false
            , retries = 0
            , timeout = 60000
    )
    private IDemoService demoService;




    /**
     * 此接口会访问2个dubbo方法
     * 第一个方法 线程休息4秒后返回
     * 第二个方法 线程休息2秒后返回
     * <p>
     * 由于使用异步. 总耗时 为4秒.
     *
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/testAsync")
    @ApiOperation(value = "testAsync")
    public String testAsync() throws ExecutionException, InterruptedException {



        logger.debug("请求B");
        demoAsyncService.testWithSleep(2000);
        // 拿到调用的Future引用，当结果返回后，会被通知和设置到此Future
        Future<String> bFuture = RpcContext.getContext().getFuture();


        logger.debug("请求A");
        // 此调用会立即返回null
        demoAsyncService.testWithSleep(4000);
        // 拿到调用的Future引用，当结果返回后，会被通知和设置到此Future
        Future<String> aFuture = RpcContext.getContext().getFuture();


        //  以上 调用顺序 不影响执行结果,先等待2秒或4秒结果相同

        // 此时testWithSleep请求同时在执行，客户端不需要启动多线程来支持并行，而是借助NIO的非阻塞完成

        // 线程wait住, 等待返回值，等待返回后，线程会被notify唤醒
        String b = bFuture.get();
        String a = aFuture.get();

        logger.debug("拿到A  {}", a);
        logger.debug("拿到B  {}", b);
        return a + b;
    }

    @GetMapping("/testAsyncWithAsync")
    @ApiOperation(value = "testAsyncWithAsync")
    public String testAsyncWithAsync() throws ExecutionException, InterruptedException {

        //使用异步姿势调用

        logger.debug("testAsyncWithAsync  start");

        demoAsyncService.testWithAsync();
        Future<String> cFuture = RpcContext.getContext().getFuture();


        logger.debug("请求A");
        // 此调用会立即返回null
        demoAsyncService.testWithSleep(4000);
        // 拿到调用的Future引用，当结果返回后，会被通知和设置到此Future
        Future<String> aFuture = RpcContext.getContext().getFuture();


        String a = aFuture.get();
        String res = cFuture.get();

        logger.debug("已经拿到值 {},  {}", res, a);
        return res + a;
    }


    /**
     * 异步调用的
     *
     *
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/testAsyncWithNoAsync")
    @ApiOperation(value = "testAsyncWithNoAsync")
    public String testAsyncWithNoAsync() throws ExecutionException, InterruptedException {

        //使用异步姿势调用

        logger.debug("testAsyncWithNoAsync  start");

        demoAsyncService.testWithNoAsync();
        Future<String> cFuture = RpcContext.getContext().getFuture();


        logger.debug("请求A");
        // 此调用会立即返回null
        demoAsyncService.testWithSleep(4000);
        // 拿到调用的Future引用，当结果返回后，会被通知和设置到此Future
        Future<String> aFuture = RpcContext.getContext().getFuture();


        String a = aFuture.get();
        String res = cFuture.get();

        logger.debug("已经拿到值 {},  {}", res, a);
        return res + a;
    }

}
