package com.ilaotan.interfaces;

import javax.validation.GroupSequence;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.ilaotan.model.ValidationParameter;

public interface IValidationService { // 缺省可按服务接口区分验证场景，如：@NotNull(groups = ValidationService.class)

//    @GroupSequence(Update.class)
            // 同时验证Update组规则
    @interface Save {} // 与方法同名接口，首字母大写，用于区分验证场景，如：@NotNull(groups = ValidationService.Save.class)，可选

    @interface Update {}

    String save(ValidationParameter parameter);

    String update(ValidationParameter parameter);

    String delete(@Min(1) int id); // 直接对基本类型参数验证

    String updateNameById(@Min(1) int id, @NotNull String name);

}