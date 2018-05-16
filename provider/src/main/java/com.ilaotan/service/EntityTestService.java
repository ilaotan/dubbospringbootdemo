package com.ilaotan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ilaotan.interfaces.IEntityTestService;
import com.ilaotan.model.TestModel;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/5/15 10:42
 */
@Service(
        version = "1.0.0"
        , application = "${dubbo.application.id}"
        , protocol = "${dubbo.protocol.id}"
        , registry = "${dubbo.registry.id}"
)
public class EntityTestService implements IEntityTestService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String test(TestModel testModel) {

        logger.error("收到参数,并解析 {}", JSON.toJSONString(testModel));

        return "success" + JSON.toJSONString(testModel);
    }
}
