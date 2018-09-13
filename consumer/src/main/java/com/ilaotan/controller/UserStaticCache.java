package com.ilaotan.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ilaotan.entity.SUser;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/7/5 15:00
 */
public class UserStaticCache {

    private static Map<String, SUser> userIdMapCache = new HashMap<>();

    private static Map<String, SUser> userPhoneMapCache = new HashMap<>();

    //模拟数据库保存三种用户
    static {
        SUser user1 = new SUser("1", "15612345671", new Date(), new Date(), "abc121");
        SUser user2 = new SUser("2", "15612345672", new Date(), new Date(), "abc122");
        SUser user3 = new SUser("3", "15612345673", new Date(), new Date(), "abc123");

        userIdMapCache.put(user1.getId(),user1);
        userIdMapCache.put(user2.getId(),user2);
        userIdMapCache.put(user3.getId(),user3);

        userPhoneMapCache.put(user1.getMobileNum(), user1);
        userPhoneMapCache.put(user2.getMobileNum(), user2);
        userPhoneMapCache.put(user3.getMobileNum(), user3);
    }


    public static SUser getUserById(String id) {
        return userIdMapCache.get(id);
    }

    public static SUser getUserByPhone(String phone) {
        return userPhoneMapCache.get(phone);
    }

    public static synchronized void saveUser(SUser sUser){
        userIdMapCache.put(sUser.getId(), sUser);
        userPhoneMapCache.put(sUser.getMobileNum(), sUser);
    }

}
