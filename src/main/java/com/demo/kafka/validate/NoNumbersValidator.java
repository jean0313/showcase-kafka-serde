package com.demo.kafka.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoNumbersValidator implements ConstraintValidator<NoNumbers, String> {
    @Override
    public void initialize(NoNumbers constraintAnnotation) {
        System.out.println("------- init ---------");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.matches(".*\\d.*");
    }
}
