package com.ilaotan.jwt;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ilaotan.controller.UserStaticCache;
import com.ilaotan.entity.SUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            //if(Constants.getConfig("jwt.validate").equals("0")){
            //    return true;
            //}
            SonliJwt annotation = method.getAnnotation(SonliJwt.class);
            if (annotation != null) {

                String token = request.getHeader("_token");
                Claims claims;

                String userId;
                String phone;

                try {

                    String json = JwtTool.getSignData(token);
                    JSONObject jsonObject = JSON.parseObject(json);
                    //拿到
                    userId = jsonObject.getString("jti");
                    phone = jsonObject.getString("iss");

                    //todo 可怕 这里需要拿到用户信息 才能保证用户的sec更新后 jwt主动拦截掉.
                    //todo 也就是说 提供api接口的服务需要依赖User基础服务
                    SUser sUser = UserStaticCache.getUserById(userId);

                    JwtTool jwtTool = new JwtTool(sUser.getSec());

                    claims = jwtTool.parseJWT(token);
                }
                catch (ExpiredJwtException e1) {
                    e1.printStackTrace();
                    // token 过期 请求用户自动登录
                    response.setHeader("_error", 1024 + "");
                    return false;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    // token其他异常
                    response.setHeader("_error", 1025 + "");
                    return false;
                }


                request.setAttribute("_jwtUserId", userId);
                request.setAttribute("_jwtPhone", phone);

            }
        }

        // 没有注解通过拦截
        return true;
    }
}