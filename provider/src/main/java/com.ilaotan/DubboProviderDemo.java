
package com.ilaotan;

import org.apache.dubbo.config.DubboShutdownHook;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;


@SpringBootApplication
@EnableDubbo
public class DubboProviderDemo {

    public static void main(String[] args) {

        SpringApplication.run(DubboProviderDemo.class,args);

    }

}
