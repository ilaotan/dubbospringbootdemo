package com.ilaotan.interfaces;

import com.ilaotan.model.TestModel;

/**
 * 测试传递实体时,若实体版本变更,会不会对接口解析有影响.
 *
 *
 *  1 提供方打包成服务 并以jar形式运行
 *  2 消费方升级model 增加一个字段
 *  3 消费方运行 看能否调用通
 *
 * @author tan liansheng on 2018/5/15 10:32
 */
public interface IEntityTestService {

    String test(TestModel testModel);
}
