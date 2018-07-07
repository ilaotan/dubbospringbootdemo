package com.ilaotan.jwt;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ilaotan.controller.UserStaticCache;
import com.ilaotan.entity.SUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.Date;
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
                Claims claims;

                String userId;
                String phone;

                try {

                    String json = JwtTool.getSignData(token);
                    JSONObject jsonObject = JSON.parseObject(json);
                    //拿到 {"jti":"1","iat":1530938587,"sub":"","iss":"15612345671","exp":1530974587}
                    userId = jsonObject.getString("jti");
                    phone = jsonObject.getString("iss");
                    // iat字段是签署jwt时设置的最后登录时间
//                    Date signDate = new Date(jsonObject.getLong("iat")* 1000);

                    //todo 可怕 这里需要拿到用户信息 才能保证用户的sec更新后 jwt主动拦截掉.
                    //todo 也就是说 提供api接口的服务需要依赖User基础服务
                    SUser sUser = UserStaticCache.getUserById(userId);

                    //以下是判断jwt合法性的姿势
                    JwtTool jwtTool = new JwtTool(sUser.getSec());
                    claims = jwtTool.parseJWT(token);

                    //  jwt合法后,才判断互踢机制
                    // 1 jwt密文中包含最后登录时间
                    // 2 此时间与数据库里记录的时间做比对
                    // 3 若数据库时间大于此条记录时间 登录失效  app重新定位到登录界面

                    // todo 这里要考虑时间精度问题 jwt默认是10位 也就是说末3位必是000 不能使用compareTo  before after等比较
//                    if (sUser.getLoginTime().compareTo(claims.getIssuedAt()) > 0) {
                    if ((sUser.getLoginTime().getTime() / 1000) > (claims.getIssuedAt().getTime() / 1000)) {
                        // 互踢机制触发
                        response.setHeader("Content-type", "text/plain;charset=UTF-8");
                        response.setHeader("_error", 1027 + "");
                        response.getWriter().write("登录失败,账号已被踢掉");
                        return false;
                    }

                }
                catch (ExpiredJwtException e1) {
                    e1.printStackTrace();
                    // token 过期 请求用户自动登录
                    response.setHeader("Content-type", "text/plain;charset=UTF-8");
                    response.setHeader("_error", 1024 + "");
                    response.getWriter().write("登录失败,token过期,请重新获取token");
                    return false;
                }
                catch (SignatureException signatureException) {
                    signatureException.printStackTrace();
                    response.setHeader("_error", 1025 + "");
                    response.setStatus(1000);

                    // fix 中文乱码
                    response.setHeader("Content-type", "text/plain;charset=UTF-8");
                    response.getWriter().write("登录失败,token签名验证失败 请重新登录");
                    return false;
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                    // token其他异常
                    response.setHeader("_error", 1026 + "");
                    response.setHeader("Content-type", "text/plain;charset=UTF-8");
                    response.getWriter().write("登录失败,其他异常,请联系管理员");
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