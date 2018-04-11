package com.ilaotan.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.ilaotan.interfaces.ICacheService;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/4/11 15:33
 */

@Service(
        version = "1.0.0"
        , application = "${dubbo.application.id}"
        , protocol = "${dubbo.protocol.id}"
        , registry = "${dubbo.registry.id}"
//        , cache = "lru"
//        , cache = "threadlocal"
        , cache = "jcache"
        // parameters的写法必须这么写(由代码反推的: @com.alibaba.dubbo.config.AbstractConfig#appendAnnotation)
        , parameters = {"cache.write.expire","30000"}
)
public class CacheServiceImpl implements ICacheService {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AtomicInteger i = new AtomicInteger();

    @Override
    public String getNews(String type) {
        String res = "request: " + type + ", response: " + i.getAndIncrement();
        logger.warn("CacheServiceImpl getNews: {}", res);
        return res;
    }
}
