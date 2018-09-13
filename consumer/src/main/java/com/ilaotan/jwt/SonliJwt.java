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

    // 内置的几种模式
    // 0 默认模式 这种模式只校验jwt的合法性
    // 1 增强模式 这种模式会先解析出用户id来 根据用户id拿用户专属的secret 使用此secret校验jwt(在用户禁止登陆等需求下可以尽快防止用户登陆)
    //           但是这种模式使用ras就恶心了,每个用户要存一个,所以只建议使用secret
    int model() default 0;
}
