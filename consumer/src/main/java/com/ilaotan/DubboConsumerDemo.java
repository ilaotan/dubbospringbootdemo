
package com.ilaotan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.ilaotan.controller.DemoConsumerController;
import com.ilaotan.jwt.JwtInterceptor;
import com.ilaotan.jwt.JwtTool;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@SpringBootApplication
// 开启swagger
@EnableSwagger2
@DubboComponentScan(basePackages = "com.ilaotan.controller")
public class DubboConsumerDemo {


    public static void main(String[] args) {

        ApplicationContext run = SpringApplication.run(DubboConsumerDemo.class, args);

        System.out.println("app start success");
    }

    //////////////////////////jwt1/////////////////////////////////
    @Bean
    public JwtInterceptor jwtInterceptor(){

        return new JwtInterceptor();
    }

//    @Bean
//    public JwtTool jwtTool(){
//        return new JwtTool("abc123");
//    }



    /**
     * 访问  8080/doc.html
     *
     * @return
     */
    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 选择那些路径和api会生成document
                .select()
                // 对该包下的api进行监控
                .apis(RequestHandlerSelectors.basePackage("com.ilaotan.controller"))
                // 对该包下的所有路径进行监控
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("dubbo-springboot-demo")
                .description("dubbo-springboot-demo")
                .termsOfServiceUrl("http://localhost:8080/")
                .contact("tanliansheng2011@gmail.com")
                .version("1.0")
                .build();
    }

}
