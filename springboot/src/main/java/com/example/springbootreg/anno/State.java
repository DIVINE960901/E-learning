package com.example.springbootreg.anno;

import com.example.springbootreg.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.*;

@Documented//元注解
@Constraint(
        validatedBy = { StateValidation.class}//指定校验规则的类
)
@Target({ElementType.FIELD})//元注解 state用哪里
@Retention(RetentionPolicy.RUNTIME)

public @interface State {

    String message() default "State的参数只能是待审核、已通过、未通过";

    Class<?>[] groups() default {};
    //获取到state的附加信息
    Class<? extends Payload>[] payload() default {};
}
