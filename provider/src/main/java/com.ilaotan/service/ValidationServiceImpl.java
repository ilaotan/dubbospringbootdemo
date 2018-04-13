package com.ilaotan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.ilaotan.interfaces.IValidationService;
import com.ilaotan.model.ValidationParameter;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/4/11 11:05
 */
@Service(
        version = "1.0.0"
        , application = "${dubbo.application.id}"
        , protocol = "${dubbo.protocol.id}"
        , registry = "${dubbo.registry.id}"
)
public class ValidationServiceImpl implements IValidationService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String save(ValidationParameter parameter) {

        logger.error("ValidationServiceImpl save: {}", parameter.toString());
        return "ValidationServiceImpl save:" + parameter.toString();
    }

    @Override
    public String update(ValidationParameter parameter) {
        logger.error("ValidationServiceImpl update: {}", parameter.toString());
        return "ValidationServiceImpl update:" + parameter.toString();
    }

    @Override
    public String delete(int id) {
        logger.error("ValidationServiceImpl delete: {}", id);
        return "ValidationServiceImpl delete:" + id;
    }

    @Override
    public String updateNameById(int id, String name) {
        logger.error("ValidationServiceImpl updateNameById: {}, {}", id, name);
        return "ValidationServiceImpl updateNameById:" + id + "," + name;
    }
}
