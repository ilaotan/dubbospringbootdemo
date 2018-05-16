package com.ilaotan.jwt;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 验证jwt字符串的合法性
 * 只有标记了注解@Jwt 的 才会走验证
 *
 * @author tanliansheng
 */
public class JwtInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            //if(Constants.getConfig("jwt.validate").equals("0")){
            //    return true;
            //}
            SonliJwt annotation = method.getAnnotation(SonliJwt.class);
            if (annotation != null) {

                String token = request.getHeader("_token");
//                System.out.println(token+"----token---");
//                Claims claims;
//
//                try {
//                    claims = jwtTool.parseJWT(token);
//                }
//                catch (ExpiredJwtException e1) {
//                    e1.printStackTrace();
//                    //
//                    response.setHeader("_error", 1024 + "");
//                    return false;
//                } catch (Exception e2) {
//                    e2.printStackTrace();
//                    response.setHeader("_error", 1025 + "");
//                    return false;
//                }
//
//
//                //可以将一些东西塞到request里方便方法内使用
////                    jwtMap.put("jti",user.getId());
////                    jwtMap.put("phone",user.getPhone());
////                    jwtMap.put("jwt",user.getJwt());
//                String userId = claims.getId();
//                String phone = claims.getIssuer();


//                request.setAttribute("_jwtUserId", userId);
//                request.setAttribute("_jwtPhone", phone);

            }
        }

        // 没有注解通过拦截
        return true;
    }
}