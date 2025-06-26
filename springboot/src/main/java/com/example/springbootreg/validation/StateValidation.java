package com.example.springbootreg.validation;

import com.example.springbootreg.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {
    /**
     *
     * @param s 要验证的数据
     * @param constraintValidatorContext
     *
     * @return
     */


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s==null){
            return false;
        }
        if(s.equals("待审核")||s.equals("已通过")||s.equals("未审核")){
            return true;
        }
        return false;
    }
}
