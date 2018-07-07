package com.ilaotan.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ilaotan.entity.SUser;
import com.ilaotan.jwt.JwtTool;
import com.ilaotan.jwt.SonliJwt;
import com.sun.org.apache.regexp.internal.REUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/7/5 14:21
 */


@RestController()
@RequestMapping("/user")
@Api(description = "UserController", tags = {"UserController"})
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/login")
    @ApiOperation(value = "login", notes = "用户登录接口")
    public String login(@RequestParam String mobileNum){

        SUser user = UserStaticCache.getUserByPhone(mobileNum);
        if(user == null) {

            return "用户不存在";
        }

        // 更新登录时间
        user.setLoginTime(new Date());

        UserStaticCache.saveUser(user);


        JwtTool jwtTool = new JwtTool(user.getSec());
        String token = jwtTool.createJWT(user.getId(), user.getMobileNum(), "", 36000);

        return token;
    }

    // 添加jwt注解
    @SonliJwt
    @GetMapping("/changePwd")
    @ApiOperation(value = "changePwd", notes = "用户修改密码接口 必须传入token")
    public String changePwd(HttpServletRequest request){

        String mobileNum = request.getParameter("_jwtPhone");

        SUser user = UserStaticCache.getUserByPhone(mobileNum);

        //这行基本触发不了
        if(user == null) {

//            return "用户不存在";
        }

        // todo 修改密码后 强制让用户重新登录???
        user.setSec(JwtTool.randomStr(6));
        UserStaticCache.saveUser(user);

        return "success";
    }



    // 添加jwt注解
    @SonliJwt
    @GetMapping("/readData")
    @ApiOperation(value = "readData", notes = "用户假装请求数据 必须传入token")
    public String readData(HttpServletRequest request){

        String mobileNum = request.getParameter("_jwtPhone");

       StringBuilder stringBuilder = new StringBuilder();
       stringBuilder.append("用户").append(mobileNum).append("你好");
       stringBuilder.append(JwtTool.randomStr(12));
        return stringBuilder.toString();
    }


}
