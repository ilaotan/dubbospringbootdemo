package com.ilaotan.interfaces;


import java.util.concurrent.ExecutionException;

import com.alibaba.dubbo.config.MethodConfig;

public interface IDemoService {

    String sayHello(String name);

    String sayHello2(String name);

    String testWithAsync() throws ExecutionException, InterruptedException;

    String testWithNoAsync();

    String testWithSleep(long millis);
}