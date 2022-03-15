package com.ilaotan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author tanli
 */
@Service
public class MyShutdownHookListener implements ApplicationListener {

    private Logger logger = LoggerFactory.getLogger(MyShutdownHookListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextClosedEvent) {
            logger.error("收到终止容器的Event. 准备停止1");
            System.out.println("收到终止容器的Event. 准备停止1");
//            try {
//                TimeUnit.SECONDS.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("收到终止容器的Event. 准备停止2");
            logger.error("收到终止容器的Event. 准备停止2");

        }
    }
}
