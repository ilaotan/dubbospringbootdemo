package com.ilaotan.interfaces;

import java.util.concurrent.ExecutionException;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/4/14 10:57
 */
public interface IDemoAsyncService {

    String testWithAsync() throws ExecutionException, InterruptedException;

    String testWithSleep(long millis);

    String testWithNoAsync();

}
