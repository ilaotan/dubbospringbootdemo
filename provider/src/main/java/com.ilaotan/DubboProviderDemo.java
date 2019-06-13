
package com.ilaotan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;


@SpringBootApplication
@DubboComponentScan(basePackages = "com.ilaotan.service")
public class DubboProviderDemo {

    public static void main(String[] args) {

        SpringApplication.run(DubboProviderDemo.class,args);

    }

}
