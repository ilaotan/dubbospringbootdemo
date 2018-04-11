
package com.ilaotan.controller;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcException;
import com.ilaotan.interfaces.IDemoService;
import com.ilaotan.interfaces.IValidationService;
import com.ilaotan.model.ValidationParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController("validate")
@Api(description = "ValidationServiceController", tags = {"ValidationServiceController"})
public class ValidationServiceController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Reference(
            version = "1.0.0"
            , check = false
            , retries = 2
            // 在客户端开启验证
            , validation = "true"
    )
    private IValidationService validationService;


    @GetMapping("/validateError")
    @ApiOperation(value = "validateError")
    public String validateError() {
        ValidationParameter parameter = new ValidationParameter();
        return validationCommon(parameter);
    }

    @GetMapping("/ok")
    @ApiOperation(value = "ok")
    public String ok() {
        ValidationParameter parameter = new ValidationParameter();
        parameter.setName("张三");
        parameter.setAge(19);
        return validationCommon(parameter);
    }

    @GetMapping("/simpleParam1")
    @ApiOperation(value = "simpleParam1")
    public String simpleParam1() {

        String message;
        try {
            // pass
            logger.info("猜测能调通--->{}", validationService.updateNameById(1, "张三"));
            // pass
            logger.info("猜测能调通--->{}", validationService.updateNameById(1, ""));
            // error
            logger.info("猜测不能调通--->{}", validationService.updateNameById(1, null));
            logger.warn("如果看到这行日志,说明接口调用没报错........................");
            message = "ok,没有错误";
        }
        catch (ConstraintViolationException ve) {
            message = getErrorMessage(ve);
        }

        return message;
    }



    private String validationCommon(ValidationParameter parameter) {
        String message;
        try {
            validationService.save(parameter);
            logger.warn("如果看到这行日志,说明接口调用没报错........................");
            message = "ok,没有错误";
        }
        catch (ConstraintViolationException ve) {
           message = getErrorMessage(ve);
        }

        return message;
    }

    private String getErrorMessage(ConstraintViolationException ve){
        String message = "";
        logger.error(ve.getMessage(), ve);
        // 可以拿到一个验证错误详细信息的集合
        Set<ConstraintViolation<?>> violations = ve.getConstraintViolations();
        StringBuilder messageBuilder = new StringBuilder(message);
        for (ConstraintViolation one : violations) {
            messageBuilder.append(one.getPropertyPath()).append(one.getMessage()).append(",");
        }
        message = messageBuilder.toString();
        if (!"".equals(message)) {
            message = message.substring(0, message.length() - 1);
        }
        logger.warn(message);
        return message;
    }

}
