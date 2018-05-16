package com.ilaotan.jwt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * 校验jwt合法性  有全局拦截器 @See JwtInterceptor 只有加注此注解的方法才会校验合法性
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SonliJwt {


}
